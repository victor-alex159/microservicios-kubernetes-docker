package com.victor.microserviciousuarios.models.controllers;

import com.victor.microserviciousuarios.models.entities.Usuario;
import com.victor.microserviciousuarios.models.services.IUsuarioService;
import com.victor.microserviciousuarios.models.util.Constants;
import com.victor.microserviciousuarios.models.util.MailService;
import com.victor.microserviciousuarios.models.util.MailServiceImpl;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.io.*;
import java.sql.Connection;
import java.util.*;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private DataSource datasource;

    @Autowired
    private MailService mailService;

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() throws Exception {
        List<Usuario> listaUsuarios = usuarioService.listar();
        return new ResponseEntity<List<Usuario>>(listaUsuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtnerUsuario(@PathVariable Integer id) throws Exception {
        Usuario usuario = usuarioService.obtenerUsuario(id);
        if(usuario != null) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws Exception {
        Usuario usuarioNuevo = usuarioService.guardar(usuario);
        if(result.hasErrors()) {
            return validarError(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

    @PutMapping()
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            return validarError(result);
        }

        Usuario usuarioEncontrado = usuarioService.obtenerUsuario(usuario.getId());
        if(usuarioEncontrado != null) {
            Usuario usuarioEdit = usuarioService.guardar(usuarioEncontrado);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioEdit);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) throws Exception {
        Usuario usuarioEncontrado = usuarioService.obtenerUsuario(id);
        if(usuarioEncontrado != null) {
            usuarioService.eliminar(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    private static ResponseEntity<Map<String, String>> validarError(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage() + ".");
        });
        return ResponseEntity.badRequest().body(errores);
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> listarUsuariosPorIds(@RequestParam List<Integer> ids) throws Exception {
        List<Usuario> listaUsuarios = usuarioService.listarPorIds(ids);
        if(listaUsuarios != null) {
            return ResponseEntity.status(HttpStatus.OK).body(listaUsuarios);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/descarga/reporte-usuario", produces = "application/x-pdf" ,method = RequestMethod.POST)
    @ResponseBody
    public void descargarReporteUsuarios(@RequestBody Usuario usuario, HttpServletResponse response) throws Exception {
        JasperPrint jasperPrint = obtenerReporteUsuario(usuario);
        response.setContentType("application/x-pdf");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

        OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        List<File> listFiles = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        fileNames.add("Reporte-usuario-1.pdf");
        fileNames.add("Reporte-usuario-2.pdf");
        File file = File.createTempFile(fileNames.get(0), null);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baos.toByteArray());
        fos.close();
        listFiles.add(file);
        listFiles.add(file);
        mailService.enviarEmail2("alexbenavente322@gmail.com", "TEST3", "CORREO DE PRUEBA", null, fileNames, listFiles);
    }

    public JasperPrint obtenerReporteUsuario(Usuario usuario) throws Exception {
        Connection connection = null;
        try {
            connection = datasource.getConnection();
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("usuarioId", usuario.getId());
            InputStream jasperInputStream = this.getClass().getClassLoader().getResourceAsStream(Constants.REPORTS_PATH + "Reporte-usuarios.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, connection);
            return jasperPrint;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
