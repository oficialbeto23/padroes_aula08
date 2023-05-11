package com.manoelcampos.exportador;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que fornece uma implementação base para as subclasses
 * que definem formatos específicos de exportação de dados.
 *
 * @author Manoel Campos da Silva Filho
 */
public abstract class AbstractExportadorListaProduto implements ExportadorListaProduto {

    private List<Coluna> colunas;

    public AbstractExportadorListaProduto() {
        colunas = new ArrayList<>();
        getColunas().add(newColuna(Produto::getId, "Código"));
        getColunas().add(newColuna(Produto::getDescricao, "Descrição"));
        getColunas().add(newColuna(Produto::getMarca, "Marca"));
    }

    @Override
    public void addColun(Coluna coluna) {
        getColunas().add(coluna);
    }

    @Override
    public final String exportar(List<Produto> listaProdutos) {
        StringBuilder sb = new StringBuilder();
        sb.append(abrirTabela());

        sb.append(abrirLinha());
        for (Coluna coluna : getColunas()){
            sb.append(coluna.exportadorCabecalho());
        }
        sb.append(fecharLinha());
        sb.append("\n");

        sb.append(abrirLinhaTitulos());
        gerarLinhasProdutos(sb, listaProdutos);

        sb.append(fecharTabela());
        return sb.toString();
    }

    /**
     * Gera o texto representando todas as linhas de uma tabela (em um formato definido pelas subclasses)
     * contendo os dados dos produtos na lista de produtos.
     *
     * @param sb {@link StringBuilder} onde o texto gerado será adicionado
     * @param listaProdutos
     */
    private void gerarLinhasProdutos(StringBuilder sb, List<Produto> listaProdutos) {
        for (Produto produto : listaProdutos) {

            sb.append(gerarColunasLinha(produto));
        }
    }

    /**
     * Gera o texto representando uma única linha de uma tabela (em um formato definido pelas subclasses).
     *
     * @param produto valores a serem exibidos nas colunas, que podem ser:
     *                (i) os títulos das colunas (caso esteja sendo gerada a linha de cabeçalho da tabela) ou
     *                (ii) os valores de uma linha da tabela (caso esteja sendo gerado uma linha de conteúdo da tabela).
     *                Neste último caso, tal parâmetro deve conter os valores dos atributos de um objeto da lista de produtos.
     * @return uma String representando a linha gerada com os valores
     */
    protected String gerarColunasLinha(Produto produto) {
        final var sb = new StringBuilder("    ");
        sb.append(abrirLinha());
        for (Coluna coluna : getColunas()) {
            sb.append(coluna.exportadorDado(produto));
        }
        sb.append(fecharLinha());
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Colunas a serem exibidas na tabela gerada no processo de exportação.
     */
    protected List<Coluna> getColunas() {
        return colunas;
    }
}
