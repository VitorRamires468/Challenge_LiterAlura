package challenge.alura.literalura.service;

public interface IConverteDados {

    <T> T converteDados(String json, Class<T> classe);
}
