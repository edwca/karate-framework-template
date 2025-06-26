function() {
  return {
    validarNumeroONull: function(valor) {
      return valor == null || karate.match(valor, '#number').pass;
    },
    isStringOrNull: function(valor) {
      return valor == null || karate.match(valor, '#string').pass;
    },
    isStringOrNumber: function(valor) {
      return karate.match(valor, '#string').pass || karate.match(valor, '#number').pass;
    }
  };
}

