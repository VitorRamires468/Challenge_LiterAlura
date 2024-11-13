package challenge.alura.literalura.model;

public enum Categoria {
        PORTUGUES("pt"),
        ESPANHOL("es"),
        INGLES("en"),
        FRANCES("fr");

        public String getCategoria() {
                return categoria;
        }

        private String categoria;

        Categoria(String categoria) {
                this.categoria = categoria;
        }

        public static Categoria fromString(String texto){
                for(Categoria categoria1:Categoria.values()){
                        if(categoria1.categoria.equalsIgnoreCase(texto)){
                                return categoria1;
                        }
                }
            throw new IllegalArgumentException("Nenhuma lingua encontrada!");
        }
}
