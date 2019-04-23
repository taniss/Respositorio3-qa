package mx.habil.evaluaciones.business.appservice;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.apachecommons.CommonsLog;
import mx.habil.evaluaciones.business.appservice.PreguntaAppService;
import mx.habil.evaluaciones.model.dto.EspecialidadDto;
import mx.habil.evaluaciones.model.dto.NivelesDto;
import mx.habil.evaluaciones.model.dto.PreguntaDto;
import mx.habil.evaluaciones.model.dto.RespuestasDto;
import mx.habil.evaluaciones.model.dto.TecnologiasDto;
import mx.habil.support.HabilAppServiceException;
import mx.habil.support.HabilDaoException;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mx/habil/evaluaciones/commons/xml/commons.aplication.context.xml",
		"classpath:mx/habil/evaluaciones/model/xml/model.aplication.context.xml",
		"classpath:mx/habil/evaluaciones/business/xml/business.aplication.context.xml",
		"classpath:mx/habil/evaluaciones/services/xml/services.aplication.context.xml" })
public class PreguntaAppServiceTestCase {

	@Autowired
	PreguntaAppService preguntaAppService;
	
	@Autowired
	@Qualifier("EvaluacionesddMMYYYYHhMmSsDateFormat")
	private SimpleDateFormat formatoFechaCompleta;
	
	/**
	 * Porposito: Test del metodo generarSetPreguntasPorEspecialidadTecnologiaYNivel
	 * 
	 * @author 	tania.portillo, Habil Mx
	 * @version 1.0, 15/04/2019
	 * @throws  HabilDaoException, si ocurre un error durante la ejecucion del metodo. 
	 * @throws IOException 
	 * @see 	PreguntaAppService#generarSetPreguntasPorEspecialidadTecnologiaYNivel(String, String, String)
	 */
	@Test
	public void testRecuperarPorEspecialidadTecnologiaNivel() throws  HabilDaoException, IOException {
		log.debug("inicio");
		
		//Declaracion de variables. 
		String especialidadStr = "Bases de Datos";
		String tecnologiaStr = "Hibernate";
		String nivelStr = "Avanzado";
		Date   fechaHoy = Calendar.getInstance().getTime();
		
		//Se recupera el listado de preguntas de la BBDD
		byte[] contenidoArchivoTxt = preguntaAppService.generarSetPreguntasPorEspecialidadTecnologiaYNivel(especialidadStr, tecnologiaStr, nivelStr);  
				
		//Se genera el archivo en ruta local para probar que se genere correctamente
		if(contenidoArchivoTxt != null) {
			log.debug("El contennido del archivo txt es diferente de nulo. ");
			
			//Se declara la ruta en donde se generara el archivo.
			String nombreArchivo = "C:/opt/ejemplo" + formatoFechaCompleta.format(fechaHoy) + ".txt"; 
			File file = new File( nombreArchivo );
			
			//Se utiliza FileUtils de commons-io
			FileUtils.writeByteArrayToFile( file, contenidoArchivoTxt);
			
		}else {
			log.debug("No se encontraron preguntas. ");
		}
		

		log.debug("fin");
	}

}