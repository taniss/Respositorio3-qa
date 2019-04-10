package mx.habil.evaluaciones.model.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.apachecommons.CommonsLog;
import mx.habil.evaluaciones.model.dto.PreguntaDto;
import mx.habil.evaluaciones.model.dto.RespuestasDto;

@CommonsLog
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = 
{
		"classpath:mx/habil/evaluaciones/commons/xml/commons.aplication.context.xml",
		"classpath:mx/habil/evaluaciones/model/xml/model.aplication.context.xml",
		"classpath:mx/habil/evaluaciones/business/xml/business.aplication.context.xml",
		"classpath:mx/habil/evaluaciones/services/xml/services.aplication.context.xml"
})
public class AlumnoDaoImplementTestCase {

	@Autowired
	PreguntaDao preguntaDao;
	
	
	
	@Test
	public void testGetById() {
		log.debug("Inicio");
		String identificadorPregunta = null;
		identificadorPregunta = "qwertyuiop23456789";

		PreguntaDto preguntaDto = null;

		preguntaDto = preguntaDao.getById(identificadorPregunta);

		if (preguntaDto != null) {
			log.debug("Objeto recuperado de BBDD: " + preguntaDto);
		} else {
			log.debug("El objeto recuperado es nulo. ");
		}

		log.debug("Fin");
	}
	
	
   /*
	@Autowired
	@Qualifier("formatofecha")
	private SimpleDateFormat fecha;
   
	
	
	@Autowired
	PreguntaDao materiaDao;

	@Test
	public void obtenmateria() {
		log.debug("Inicio");
		Integer identificadorMateria = null;
		identificadorMateria = 4;

		MateriaDto materiaDto = null;

		materiaDto = materiaDao.recuperarPorId(identificadorMateria);

		// validar si el id es nulo
		if (materiaDto != null) {
			log.debug("Objeto recuperado de BBDD: " + materiaDto);
		} else {
			log.debug("El objeto recuperado es nulo. ");
		}

		log.debug("Fin");

	}

	@Test
	public void getById() {
		log.debug("Inicio");
		String identificadorAlumno = null;
		identificadorAlumno = "5QWERTY";

		PreguntaDto preguntaDto = null;

		preguntaDto = preguntaDao.getById(identificadorAlumno);

		if (preguntaDto != null) {
			log.debug("Objeto recuperado de BBDD: " + preguntaDto);
		} else {
			log.debug("El objeto recuperado es nulo. ");
		}

		log.debug("Fin");
	}

	@Test
	public void recuperarPorId() {
		log.debug("Inicio");
		String identificadorPregunta = null;
		identificadorPregunta = "01ecb9d0-27a3-4377-a280-2b673452a2ba";
		PreguntaDto preguntaDto = null;

		preguntaDto = preguntaDao.recuperarPorId(identificadorPregunta);

		if (preguntaDto != null) {
			log.debug("Objeto recuperado de BBDD: " + preguntaDto);

			log.debug("alumnomateria: " + preguntaDto.getMaterias());
		} else {
			log.debug("El objeto recuperado es nulo. ");
		}

		log.debug("Fin");
	}

	@Test
	public void guardarRegistro() {
		log.debug("inicio");

		// inicializacion de variables para la prueba
        PreguntaDto preguntaDto = null;

		preguntaDto = new PreguntaDto();

		preguntaDto.setNombreAlumno("juan");
		preguntaDto.setApellidoMaterno("hernandez");
		preguntaDto.setApellidoPaterno("perez");
		preguntaDto.setFechaIngreso("2019-2-13");
		preguntaDto.setMatricula("Metr");

		// SE CREA LA COLECCION DE MATERIAS VACIO
		Set<MateriaDto> setMaterias = new HashSet<MateriaDto>();

		{// AGREGANDO MATERIAS A LA COLECCION

			// REGISTRO UNO
			// Nuevo registro de materia - español -
			MateriaDto materiaUno = new MateriaDto();
			materiaUno.setNombreMateria("fisica");
			materiaUno.setSemestreMateria("segundo");

			// ligar la materia de español al alumno
			materiaUno.setAlumno(preguntaDto);

			// Agregar materia de español al listado
			setMaterias.add(materiaUno);

		}

		// Setear el set de materias al alumno
		preguntaDto.setMaterias(setMaterias);

		log.debug("objeto:" + preguntaDto);

		preguntaDao.save(preguntaDto);

		log.debug("fin");

	}

	@Test
	public void actualizar() {
		log.debug("inicio actualizar");

		// recuperamos los datos del alumno por el id
		String identificador = "44d0ceca-9766-48c8-9407-ed9edf06e942";
		PreguntaDto preguntaDto = null;
		preguntaDto = preguntaDao.recuperarPorId(identificador);

		// convertir una fecha de tipo date a string
		// String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		// verificamos el contenido de alumnoDto
		if (preguntaDto != null) {
			log.debug("Objeto recuperado de BBDD: " + preguntaDto);
		} else {
			log.debug("El objeto recuperado es nulo. ");
		}

		// llenamos los nuevos datos a actualizar
		preguntaDto.setNombreAlumno("luis act");
		preguntaDto.setApellidoPaterno("perez act");
		preguntaDto.setApellidoMaterno("hernandez atc");
		preguntaDto.setFechaIngreso(fecha.format(new Date()));

		// String fecha2 = "2017-11-23";
		// alumnoDto.setFechaIngreso(fecha.parse(fecha2));

		// actualizamos los datos correspondientes al id
		preguntaDao.update(preguntaDto);

		// imprimimos los nuevos datos
		log.debug("Objeto recuperado de BBDD: " + preguntaDto);

		log.debug("fin actualizar");

	}

	@Test
	public void borrar() {
		log.debug("inicio de borrado");
		// recuperamos los datos del alumno por id
		String identificador = "8f5dfa4a-bd0c-48c1-b4aa-0b81fe17a6e4";
		PreguntaDto preguntaDto = null;

		preguntaDto = preguntaDao.recuperarPorId(identificador);

		// verificamos el contenido de alumnoDto
		if (preguntaDto == null) {
			log.debug("El objeto recuperado es nulo. ");
		} else {
			log.debug("prueba");
			// eliminamos al alumno por id
			preguntaDao.delete(preguntaDto);
			log.debug("el objeto se borro correctamente: ");
		}

		log.debug("fin de borrado");
	}
	*/
}