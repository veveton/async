package br.com.surubim.endpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.surubim.model.OfertasDomain;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("farm")
public class FarmaciaEndpoint {

	@Autowired
	private NaMinhaMaquinaFunciona agoraVai;

	private StopWatch watch;

//	@Autowired
//	private ItensRepository itensDao;

	// METODOS PARA TESTE DA REQUISIÇÃO ASYNC "NÃO FUNCIONA MAIS"

	// localhost:8080/farm/metodoChamadorAsync

	@GetMapping(path = "/metodoChamadorAsync")
	public ResponseEntity<?> metodoChamador() throws InterruptedException, ExecutionException {
		watch = new StopWatch();
		watch.start();

		log.info("Calling asyncMethodOne " + System.nanoTime());
		Future<List<OfertasDomain>> return1 = asyncMethodOne();

		log.info("Calling asyncMetodTwo " + System.nanoTime());
		Future<List<OfertasDomain>> return2 = asyncMethodTwo();

		List<OfertasDomain> retornoAll = new ArrayList<>();

		log.info("get asyncMethodOne " + System.nanoTime());
		retornoAll.addAll(return1.get());

		log.info("get asyncMethodTwo " + System.nanoTime());
		retornoAll.addAll(return2.get());

		log.info("Return time " + System.nanoTime());

		for (OfertasDomain ofertasDomain : retornoAll) {
			log.info("Name: " + ofertasDomain.getName() + " value: " + ofertasDomain.getValor());
		}
		watch.stop();

		log.info("Total time lapsed " + watch.getTotalTimeMillis() + "ms");

		return new ResponseEntity<>(retornoAll, HttpStatus.OK);
	}

	// REQUISIÇÃO DE METODOS ASYNC PRESENTE EM OUTRA CLASSE "FUNCIONA"

	@GetMapping(path = "/metodoChamadorAsync2")
	public ResponseEntity<?> metodoChamadorAsync() throws InterruptedException, ExecutionException {

		this.watch = new StopWatch();
		this.watch.start();

		log.info("Calling asyncMethodOne " + System.nanoTime());
		Future<List<OfertasDomain>> rt1 = agoraVai.asyncMethodOne();

		log.info("Calling asyncMetodTwo " + System.nanoTime());
		Future<List<OfertasDomain>> rt2 = agoraVai.asyncMethodTwo();

		List<OfertasDomain> rt3 = new ArrayList<OfertasDomain>();

		log.info("get asyncMethodOne " + System.nanoTime());
		rt3.addAll(rt1.get());

		log.info("get asyncMethodTwo " + System.nanoTime());
		rt3.addAll(rt2.get());

		watch.stop();

		log.info("Total time lapsed " + this.watch.getTotalTimeMillis() + "ms");
		return new ResponseEntity<>(rt3, HttpStatus.OK);
	}

	// REQUISIÇÃO CHAMANDO UM METODO DE OUTRA CLASSE QUE UTILIZA METODOS ASYNC "NÃO
	// FUNCIONA"
	@GetMapping(path = "/metodoChamadorAsync3")
	public ResponseEntity<?> metodoChamadorAsync3() throws InterruptedException, ExecutionException {
		List<OfertasDomain> retorno = agoraVai.executaTudo();

		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@Async
	public Future<List<OfertasDomain>> asyncMethodOne() throws InterruptedException {
		log.info("Into asyncMethodOne " + System.nanoTime());
		OfertasDomain item = new OfertasDomain();
		item.setName("1");
		item.setValor(12);
		log.info("Start sleep asyncMethodOne 3000ms");
		Thread.sleep(3000);
		log.info("End sleep asyncMethodOne 3000ms");
		return new AsyncResult<List<OfertasDomain>>(Arrays.asList(item));
	}

	@Async
	public Future<List<OfertasDomain>> asyncMethodTwo() throws InterruptedException {
		log.info("Into asyncMethodTwo " + System.nanoTime());
		OfertasDomain item = new OfertasDomain();
		item.setName("2");
		item.setValor(22);
		log.info("Start sleep asyncMethodTwo 2000ms");
		Thread.sleep(2000);
		log.info("End sleep asyncMethodTwo 2000ms");
		return new AsyncResult<List<OfertasDomain>>(Arrays.asList(item));
	}

	// METODO COM CHAMADA SYNC PARA COMPARAÇÃO DE TEMPO DE EXECUÇÃO

	// localhost:8080/farm/metodoChamadorSync

	@GetMapping(path = "/metodoChamadorSync")
	private ResponseEntity<?> metodoChamadorSync() throws InterruptedException {
		watch = new StopWatch();
		watch.start();

		log.info("Calling syncMethodOne " + System.nanoTime());
		List<OfertasDomain> retorno1 = syncMethodOne();

		log.info("Calling syncMetodTwo " + System.nanoTime());
		List<OfertasDomain> retorno2 = syncMethodTwo();

		List<OfertasDomain> retornoAll = new ArrayList<>();

		log.info("get syncMethodOne " + System.nanoTime());
		retornoAll.addAll(retorno1);

		log.info("get syncMethodOne " + System.nanoTime());
		retornoAll.addAll(retorno2);

		log.info("Return time " + System.nanoTime());

		for (OfertasDomain ofertasDomain : retornoAll) {
			log.info("Name: " + ofertasDomain.getName() + " value: " + ofertasDomain.getValor());
		}
		watch.stop();

		log.info("Total time lapsed " + watch.getTotalTimeMillis() + "ms");

		return new ResponseEntity<>(retornoAll, HttpStatus.OK);
	}

	public List<OfertasDomain> syncMethodOne() throws InterruptedException {
		log.info("Into syncMethodOne " + System.nanoTime());
		OfertasDomain item = new OfertasDomain();
		item.setName("1");
		item.setValor(12);
		log.info("Start sleep syncMethodOne 10000ms");
		Thread.sleep(10000);
		log.info("End sleep syncMethodOne 10000ms");
		return Arrays.asList(item);
	}

	public List<OfertasDomain> syncMethodTwo() throws InterruptedException {
		log.info("Into syncMethodTwo " + System.nanoTime());
		OfertasDomain item = new OfertasDomain();
		item.setName("2");
		item.setValor(22);
		log.info("Start sleep syncMethodTwo 5000ms");
		Thread.sleep(5000);
		log.info("End sleep syncMethodTwo 5000ms");
		return Arrays.asList(item);
	}

//	@GetMapping(path = "/all")
//	public ResponseEntity<?> listAll(HttpServletRequest request) throws JsonProcessingException {
//		watch = new StopWatch();
//		watch.start();
//		Long timeStart = System.nanoTime();
//		Iterable<ItemEntity> returnItens = itensDao.findAll();
//		watch.stop();
//		log.info(LoggerController.getFormatter(request, timeStart, System.nanoTime(), watch.getTotalTimeMillis(),
//				returnItens));
//		return new ResponseEntity<>(returnItens, HttpStatus.OK);
//	}
//
//	@GetMapping(path = "item/{id}")
//	public ResponseEntity<?> getItemtById(HttpServletRequest request, @PathVariable("id") Long id)
//			throws JsonProcessingException {
//		watch = new StopWatch();
//		watch.start();
//		Long timeStart = System.nanoTime();
//		Optional<ItemEntity> item = null;
//		try {
//			verifyIfItemExists(id);
//			item = itensDao.findById(id);
//			watch.stop();
//			log.info(LoggerController.getFormatter(request, timeStart, System.nanoTime(), watch.getTotalTimeMillis(),
//					item.get()));
//		} catch (ResourceNotFoundException e) {
//			watch.stop();
//			log.error(LoggerController.getFormatterError(e, request, timeStart, System.nanoTime(),
//					watch.getTotalTimeMillis()));
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//
//		return new ResponseEntity<>(item, HttpStatus.OK);
//	}
//
//	@PostMapping(path = "item/save")
//	@Transactional(rollbackFor = Exception.class)
//	public ResponseEntity<?> save(@Validated @RequestBody ItemEntity item, HttpServletRequest request)
//			throws JsonProcessingException {
//		watch = new StopWatch();
//		watch.start();
//		Long timeStart = System.nanoTime();
//		ItemEntity returnSave = itensDao.save(item);
//		watch.stop();
//		log.info(LoggerController.getFormatter(request, item, timeStart, System.nanoTime(), watch.getTotalTimeMillis(),
//				returnSave));
//		return new ResponseEntity<>(returnSave, HttpStatus.CREATED);
//	}
//
//	private void verifyIfItemExists(Long id) {
//		if (!itensDao.existsById(id)) {
//			throw new ResourceNotFoundException("ResourceNotFoundException");
//		}
//	}
}
