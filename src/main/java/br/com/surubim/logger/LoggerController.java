package br.com.surubim.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoggerController {
	private static ObjectMapper mapper = new ObjectMapper();

//	 url, headers, queryparams, body da chamada(se tiver),
//	 tempo da requisição total, hora de início e de fim, body do
//	 retorno
	public static String getFormatter(HttpServletRequest request, Object bodyEntrada, Long hInicio, Long hFim,
			long timeLapse, Object bodyRetorno) throws JsonProcessingException {

		StringBuilder retorno = new StringBuilder();
		retorno.append("URL: ").append(request.getRequestURL().toString()).append(" Headers: ")
				.append(getHeaders(request)).append(" Queryparams: ").append(request.getQueryString())
				.append(" Body Entrada: ").append(mapper.writeValueAsString(bodyEntrada))
				.append(formatTimesMilis(hInicio, hFim, timeLapse)).append(" Body Retorno: ")
				.append(mapper.writeValueAsString(bodyRetorno));
		return retorno.toString();

	}

	public static String getFormatter(String url, String headers, String queryparams, String bodyEntrada,
			String tempoRequisicao, String hInicio, String hFim, Object bodyRetorn) {
		return new String(url + headers + queryparams + bodyEntrada + tempoRequisicao + hInicio + hFim + bodyRetorn);
	}

	public static String getFormatter(HttpServletRequest request, Long hInicio, Long hFim, long timeLapse,
			Object bodyRetorno) throws JsonProcessingException {
		return getFormatter(request, " ", hInicio, hFim, timeLapse, bodyRetorno);
	}

	public static String getFormatterError(Exception e, HttpServletRequest request, Long hInicio, Long hFim,
			long timeLapse) throws JsonProcessingException {
		StringBuilder msgError = new StringBuilder();
		msgError.append(e.getMessage()).append(" ").append(getFormatter(request, " ", hInicio, hFim, timeLapse, " "))
				.append(" \n ").append(ExceptionUtils.getStackTrace(e));
		return msgError.toString();
	}

	private static String getHeaders(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();

		StringBuilder retorno = new StringBuilder();
		while (headerNames.hasMoreElements()) {

			String headerName = headerNames.nextElement();
			retorno.append("headerName= ").append(headerName);

			Enumeration<String> headers = request.getHeaders(headerName);
			while (headers.hasMoreElements()) {
				String headerValue = headers.nextElement();
				retorno.append(" headerValue= ").append(headerValue);
			}
			if (headerNames.hasMoreElements()) {
				retorno.append(", ");
			}
		}
		return retorno.toString();
	}

	private static String formatTimesMilis(long timeI, long timeF, long timeLapse) {
		StringBuilder retorno = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
		retorno.append(" Tempo Total: ").append(timeLapse).append("ms Tempo Inicio: ")
				.append(sdf.format(new Date(timeI))).append(" Tempo Fim: ").append(sdf.format(new Date(timeF)));
		return retorno.toString();
	}

}
