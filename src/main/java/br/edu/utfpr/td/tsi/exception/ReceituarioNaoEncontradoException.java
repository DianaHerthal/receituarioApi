package br.edu.utfpr.td.tsi.exception;

public class ReceituarioNaoEncontradoException extends RuntimeException {

    public ReceituarioNaoEncontradoException() {
        super("Receituario não encontrado.");
    }

    public ReceituarioNaoEncontradoException(String id) {
        super("Receituario não encontrado com ID: " + id);
    }
}