package mx.habil.evaluaciones.model.dao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class PreguntaDaoImplTestCase {

	@Autowired
	PreguntaDao preguntaDao;

	@Test
	public void testGetById() {
		log.debug("Inicio");
		String identificadorPregunta = null;
		identificadorPregunta = "qwertyuiop2";

		PreguntaDto preguntaDto = null;

		preguntaDto = preguntaDao.getById(identificadorPregunta);

		if (preguntaDto != null) {
			log.debug("Objeto recuperado de BBDD: " + preguntaDto);
		} else {
			log.debug("El objeto recuperado es nulo. ");
		}

		log.debug("Fin");
	}

	@Test
	public void testRecuperarPorId() {
		log.debug("Inicio");

		String identificadorPregunta = null;

		identificadorPregunta = "qwertyuiop2";

		PreguntaDto result = preguntaDao.recuperarPorId(identificadorPregunta);

		if (result == null) {
			log.debug("No existe un registro para el id " + identificadorPregunta);
		} else {
			log.debug("Objeto recuperado: " + result);
		}

		log.debug("Fin");
	}

	/**
	 * Proposito: Prueba para guardar un nuevo registro en la tabla de Pregunta
	 * 
	 * @author tania.portillo, Habil Mx
	 * @version 1.0.0, 11/04/2019
	 * 
	 */
	@Test
	public void testGuardarNuevoRegistro() {
		log.debug("Inicio");

		PreguntaDto preguntaDto = null;

		// Crear una nueva instancia de preguntaDto.
		preguntaDto = new PreguntaDto();

		// Setear informacion para guardar en BBDD
		
		// spreguntaDto.setIdPregunta(idPregunta); // Este dato JAMAS se debe de setear
		// ya que se asigna automaticamente
		preguntaDto.setPregunta("Que es lo que carateriza a Hirbenate?");

		// 1.- Crear set de respuestas.
		Set<RespuestasDto> lsRespuesta = new HashSet<RespuestasDto>();

		RespuestasDto respuestasDto = new RespuestasDto();
		respuestasDto.setRespuesta("Hirbenate es orientado objetos");
		respuestasDto.setPreguntaDto(preguntaDto);

		lsRespuesta.add(respuestasDto);

		preguntaDto.setLsRespuesta(lsRespuesta);

		// 2.- Crear set de niveles.
		NivelesDto nivelesDto = new NivelesDto();
		Set<PreguntaDto> lstPreguntas = new HashSet<PreguntaDto>();
		lstPreguntas.add(preguntaDto);

		nivelesDto.setNombreNivel("B·sico");
		nivelesDto.setLsPregunta(lstPreguntas);
		preguntaDto.setNivelesDto(nivelesDto);

		// 3.- Crear set de tecnologias.
		TecnologiasDto tecnologiasDto = new TecnologiasDto();
		tecnologiasDto.setNombreTecnologia("Hibernate");
		tecnologiasDto.setDescripcionTecnologia("Framework para BBDD");

		EspecialidadDto especialidadDto = new EspecialidadDto();
		especialidadDto.setNombreEspecialidad("Bases de Datos");
		especialidadDto.setDescripcion("Todo lo relacionado con BBDDD");

		Set<TecnologiasDto> lsTecnologias = new HashSet<TecnologiasDto>();
		lsTecnologias.add(tecnologiasDto);
		especialidadDto.setLsTecnologias(lsTecnologias);

		tecnologiasDto.setEspecialidadDto(especialidadDto);
		preguntaDto.setTecnologiasDto(tecnologiasDto);

		// Guardar registro en la Base de Datos.
		preguntaDao.save(preguntaDto);

		log.debug("Fin");
	}

	/**
	 * 
	 */
	@Test
	public void testActualizar() {
		log.debug("Inicio");

		// 1.- Recuperar registro por id
		String identificadorPregunta = "4f6fd558-8231-492b-8f74-0511edc4fd7f";

		PreguntaDto result = preguntaDao.recuperarPorId(identificadorPregunta);

		// 2.- Validar y actualizar el registro
		if (result != null) {

			// Setear nueva informacion de la pregunta
			result.setPregunta("øHQL?");

			// Actualizar dto
			preguntaDao.update(result);

		} else {
			log.debug("No se encontro un registro para el identificador: " + identificadorPregunta);
		}
		log.debug("Fin");
	}

	@Test
	public void testEliminar() {
		log.debug("Inicio");
		// 1.- Recuperar registro por id
		String identificadorPregunta = "1q2w3e";

		PreguntaDto result = preguntaDao.recuperarPorId(identificadorPregunta);

		log.debug(result);
		// 2.- Validar y Eliminar el registro
		if (result != null) {
			log.debug("El objeto recuperado es nulo. ");
			// 3.- Eliminar dto.
			preguntaDao.delete(result);
			log.debug("El objeto se borro correctamente: ");

		} else {
			log.debug("No se encontro un registro para el identificador: " + identificadorPregunta);

		}

		log.debug("Fin");
	}

	
	
	/**
	 * Porposito: Test del metodo recuperarPorEspecialidadTecnologiaNivel
	 * 
	 * @author 	tania.portillo, Habil Mx
	 * @version 1.0, 15/04/2019
	 * @throws  HabilDaoException, si ocurre un error durante la ejecucion del metodo. 
	 * @see 	PreguntaDao#recuperarPorEspecialidadTecnologiaNivel(String, String, String)
	 */
	@Test
	public void testRecuperarPorEspecialidadTecnologiaNivel() throws  HabilDaoException {
		log.debug("inicio");
		
		//Declaracion de variables. 
		String especialidadStr = "Bases de Datos";
		String tecnologiaStr = "Hibernate";
		String nivelStr = "Avanzado";
		
		//Se recupera el listado de preguntas de la BBDD
		List<String> listaPreguntas = preguntaDao.recuperarPorEspecialidadTecnologiaNivel(especialidadStr, tecnologiaStr, nivelStr);  
				
		//Se imprime el resultado en la consola.
		if(listaPreguntas != null && !listaPreguntas.isEmpty()) {
			log.debug("Lista recuperada: " + listaPreguntas);
			
			//Se itera la lista para imprimir el contenido en la consola. 
			for(String pregunta : listaPreguntas) {
				
				log.debug( pregunta );
			}
			
			
		}else {
			log.debug("No se encontraron preguntas. ");
		}
		

		log.debug("fin");
	}


	/*
	 * @Autowired
	 * 
	 * @Qualifier("formatofecha") private SimpleDateFormat fecha;
	 * 
	 * 
	 * 
	 * @Autowired PreguntaDao materiaDao;
	 * 
	 * @Test public void obtenmateria() { log.debug("Inicio"); Integer
	 * identificadorMateria = null; identificadorMateria = 4;
	 * 
	 * MateriaDto materiaDto = null;
	 * 
	 * materiaDto = materiaDao.recuperarPorId(identificadorMateria);
	 * 
	 * // validar si el id es nulo if (materiaDto != null) {
	 * log.debug("Objeto recuperado de BBDD: " + materiaDto); } else {
	 * log.debug("El objeto recuperado es nulo. "); }
	 * 
	 * log.debug("Fin");
	 * 
	 * }
	 * 
	 * @Test public void getById() { log.debug("Inicio"); String identificadorAlumno
	 * = null; identificadorAlumno = "5QWERTY";
	 * 
	 * PreguntaDto preguntaDto = null;
	 * 
	 * preguntaDto = preguntaDao.getById(identificadorAlumno);
	 * 
	 * if (preguntaDto != null) { log.debug("Objeto recuperado de BBDD: " +
	 * preguntaDto); } else { log.debug("El objeto recuperado es nulo. "); }
	 * 
	 * log.debug("Fin"); }
	 * 
	 * @Test public void recuperarPorId() { log.debug("Inicio"); String
	 * identificadorPregunta = null; identificadorPregunta =
	 * "01ecb9d0-27a3-4377-a280-2b673452a2ba"; PreguntaDto preguntaDto = null;
	 * 
	 * preguntaDto = preguntaDao.recuperarPorId(identificadorPregunta);
	 * 
	 * if (preguntaDto != null) { log.debug("Objeto recuperado de BBDD: " +
	 * preguntaDto);
	 * 
	 * log.debug("alumnomateria: " + preguntaDto.getMaterias()); } else {
	 * log.debug("El objeto recuperado es nulo. "); }
	 * 
	 * log.debug("Fin"); }
	 * 
	 * @Test public void guardarRegistro() { log.debug("inicio");
	 * 
	 * // inicializacion de variables para la prueba PreguntaDto preguntaDto = null;
	 * 
	 * preguntaDto = new PreguntaDto();
	 * 
	 * preguntaDto.setNombreAlumno("juan");
	 * preguntaDto.setApellidoMaterno("hernandez");
	 * preguntaDto.setApellidoPaterno("perez");
	 * preguntaDto.setFechaIngreso("2019-2-13"); preguntaDto.setMatricula("Metr");
	 * 
	 * // SE CREA LA COLECCION DE MATERIAS VACIO Set<MateriaDto> setMaterias = new
	 * HashSet<MateriaDto>();
	 * 
	 * {// AGREGANDO MATERIAS A LA COLECCION
	 * 
	 * // REGISTRO UNO // Nuevo registro de materia - espa√±ol - MateriaDto
	 * materiaUno = new MateriaDto(); materiaUno.setNombreMateria("fisica");
	 * materiaUno.setSemestreMateria("segundo");
	 * 
	 * // ligar la materia de espa√±ol al alumno materiaUno.setAlumno(preguntaDto);
	 * 
	 * // Agregar materia de espa√±ol al listado setMaterias.add(materiaUno);
	 * 
	 * }
	 * 
	 * // Setear el set de materias al alumno preguntaDto.setMaterias(setMaterias);
	 * 
	 * log.debug("objeto:" + preguntaDto);
	 * 
	 * preguntaDao.save(preguntaDto);
	 * 
	 * log.debug("fin");
	 * 
	 * }
	 * 
	 * @Test public void actualizar() { log.debug("inicio actualizar");
	 * 
	 * // recuperamos los datos del alumno por el id String identificador =
	 * "44d0ceca-9766-48c8-9407-ed9edf06e942"; PreguntaDto preguntaDto = null;
	 * preguntaDto = preguntaDao.recuperarPorId(identificador);
	 * 
	 * // convertir una fecha de tipo date a string // String fecha = new
	 * SimpleDateFormat("yyyy-MM-dd").format(new Date());
	 * 
	 * // verificamos el contenido de alumnoDto if (preguntaDto != null) {
	 * log.debug("Objeto recuperado de BBDD: " + preguntaDto); } else {
	 * log.debug("El objeto recuperado es nulo. "); }
	 * 
	 * // llenamos los nuevos datos a actualizar
	 * preguntaDto.setNombreAlumno("luis act");
	 * preguntaDto.setApellidoPaterno("perez act");
	 * preguntaDto.setApellidoMaterno("hernandez atc");
	 * preguntaDto.setFechaIngreso(fecha.format(new Date()));
	 * 
	 * // String fecha2 = "2017-11-23"; //
	 * alumnoDto.setFechaIngreso(fecha.parse(fecha2));
	 * 
	 * // actualizamos los datos correspondientes al id
	 * preguntaDao.update(preguntaDto);
	 * 
	 * // imprimimos los nuevos datos log.debug("Objeto recuperado de BBDD: " +
	 * preguntaDto);
	 * 
	 * log.debug("fin actualizar");
	 * 
	 * }
	 * 
	 * @Test public void borrar() { log.debug("inicio de borrado"); // recuperamos
	 * los datos del alumno por id String identificador =
	 * "8f5dfa4a-bd0c-48c1-b4aa-0b81fe17a6e4"; PreguntaDto preguntaDto = null;
	 * 
	 * preguntaDto = preguntaDao.recuperarPorId(identificador);
	 * 
	 * // verificamos el contenido de alumnoDto if (preguntaDto == null) {
	 * log.debug("El objeto recuperado es nulo. "); } else { log.debug("prueba"); //
	 * eliminamos al alumno por id preguntaDao.delete(preguntaDto);
	 * log.debug("el objeto se borro correctamente: "); }
	 * 
	 * log.debug("fin de borrado"); }
	 */
}