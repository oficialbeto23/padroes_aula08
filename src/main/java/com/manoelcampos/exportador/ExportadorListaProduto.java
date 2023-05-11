package com.manoelcampos.exportador;

import java.util.List;
import java.util.function.Function;

/**
 * Define a interface para criação de classes que implementam
 * a exportação de uma lista de produtos para
 * formatos específicos como HTML, CSV, Markdown, etc.
 *
 * @author Manoel Campos da Silva Filho
 */
public interface ExportadorListaProduto {
    String abrirTabela();
    String fecharTabela();
    String abrirLinha();
    String fecharLinha();
    String abrirLinhaTitulos();
    String fecharLinhaTitulos();


    String exportar(List<Produto> listaProdutos);

    Coluna newColuna(Function<Produto, Object> obtemValorColuna, String titulo);
    void addColun(Coluna coluna);



    static ExportadorListaProduto newInstance(){
        return newInstance("html");
    }

    /**
     * Cria uma instância de uma classe
     * que realiza a exportação de dados para um formato definido.
     * @param extensaoArqExportacao extensão de arquivo que indica o formato para converter os dados,
     *                                  como html, csv, md (markdown), etc.
     * @return
     */
    static ExportadorListaProduto newInstance(String extensaoArqExportacao){
        /*Esta forma de escrever um switch é chamada de "Switch Expression",
        * onde podemos fazer o switch retornar um valor e armazenar numa variável.
        * Tal recurso foi introduzido definitivamente no JDK 14. */
        return switch (extensaoArqExportacao) {
            case "html" -> new ExportadorListaProdutoHtml();
            case "md" -> new ExportadorListaProdutoMarkdown();
            default -> throw new UnsupportedOperationException("Formato de arquivo não suportado: " + extensaoArqExportacao);
        };
    }
}
