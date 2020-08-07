package br.com.surubim.endpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.StopWatch;

import br.com.surubim.model.OfertasDomain;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NaMinhaMaquinaFunciona {
	StopWatch watch;

	public List<OfertasDomain> executaTudo() throws InterruptedException, ExecutionException {

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

		return retornoAll;
	}

	@Async
	public Future<List<OfertasDomain>> asyncMethodOne() throws InterruptedException {
		log.info("Into asyncMethodOne " + System.nanoTime());
		OfertasDomain item = new OfertasDomain();
		item.setName("1");
		item.setValor(12);
		log.info("Start sleep asyncMethodOne 10000ms");
		Thread.sleep(10000);
		log.info("End sleep asyncMethodOne 10000ms");
		return new AsyncResult<List<OfertasDomain>>(Arrays.asList(item));
	}

	@Async
	public Future<List<OfertasDomain>> asyncMethodTwo() throws InterruptedException {
		log.info("Into asyncMethodTwo " + System.nanoTime());
		OfertasDomain item = new OfertasDomain();
		item.setName("2");
		item.setValor(22);
		log.info("Start sleep asyncMethodTwo 5000ms");
		Thread.sleep(5000);
		log.info("End sleep asyncMethodTwo 5000ms");
		return new AsyncResult<List<OfertasDomain>>(Arrays.asList(item));
	}
}
