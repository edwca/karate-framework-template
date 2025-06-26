function() {
  var File = Java.type('java.io.File');
  var Files = Java.type('java.nio.file.Files');
  var StandardCopyOption = Java.type('java.nio.file.StandardCopyOption');

  return {
    copyFile: function (nombreArchivo, destino) {
      // carpeta de origen desde classpath-> "C:\workspaces\ApiTesting\"target\test-classes\files"
      // compilado desde "\src\resources\files\DOCUMENTO DE PRUEBA CONTENT.pdf"
      var origen = new File(karate.toAbsolutePath('classpath:files/' + nombreArchivo));
      var destinoFinal = new File(destino + '/' + nombreArchivo);

      if (!destinoFinal.exists()) {
        Files.copy(origen.toPath(), destinoFinal.toPath(), StandardCopyOption.REPLACE_EXISTING);
        karate.log('✅ Archivo copiado:', destinoFinal.getAbsolutePath());
      } else {
        karate.log('ℹ️ Ya existe en destino:', destinoFinal.getAbsolutePath());
      }

      return destinoFinal.exists();
    },

    directoryExists: function (ruta) {
        var carpeta = new File(ruta);
        var exists = carpeta.exists();
        var isDir = carpeta.isDirectory();
        var canRead = carpeta.canRead();
        var canWrite = carpeta.canWrite();

        karate.log('📁 Verificando ruta:', ruta);
        karate.log('📌 exists:', exists, '| isDirectory:', isDir, '| canRead:', canRead, '| canWrite:', canWrite);

        return exists && isDir && (canRead || canWrite);
    },
    deleteFileIfExists: function (rutaCompleta) {
      var archivo = new File(rutaCompleta);
      if (archivo.exists()) {
        var deleted = archivo.delete();
        karate.log(deleted ? '🗑️ Archivo eliminado:' : '⚠️ No se pudo eliminar:', rutaCompleta);
        return deleted;
      }
      karate.log('ℹ️ Archivo no existe, no hay que eliminar:', rutaCompleta);
      return true;
    }
  };
}
