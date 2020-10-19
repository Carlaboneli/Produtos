package com.example.produtos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.produtos.Modelo.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private final int RESULT_CODE_NOVO_PRODUTO = 10;
    private final int RESULT_CODE_PRODUTO_EDITADO = 11;

    private boolean edicao = false;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        setTitle("Cadastro de Produto");

        Intent intent = getIntent();
        if ( intent != null && intent.getExtras() != null &&
                intent.getExtras().get("produtoEdicao") != null) {
            Produto produto = (Produto) intent.getExtras().get("produtoEdicao");
            EditText editTextNome = findViewById(R.id.editText_Nome);
            EditText editTextvalor = findViewById(R.id.editText_valor);
            editTextNome.setText(produto.getNome());
            editTextvalor.setText(String.valueOf(produto.getValor()));

        }

        carregarProduto();

        }

    private void carregarProduto () {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("produtoEdicao") != null){
            Produto produto = (Produto) intent.getExtras().get("produtoEdicao");
            EditText editTextNome = findViewById(R.id.editText_Nome);
            EditText editTextValor = findViewById(R.id.editText_valor);
            editTextNome.setText(produto.getNome());
            editTextValor.setText(String.valueOf(produto.getValor()));
            edicao = true;
            id = produto.getId();
    }}

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v){
        EditText editTextNome = findViewById(R.id.editText_Nome);
        EditText editTextValor = findViewById(R.id.editText_valor);

        String nome =  editTextNome.getText().toString();
        Float valor = Float.parseFloat(editTextValor.getText().toString());

        Produto produto = new  Produto(id,nome,valor);
        Intent intent = new Intent();

        if (edicao){
            intent.putExtra("produtoEditado", produto);
            setResult(RESULT_CODE_PRODUTO_EDITADO, intent);
        } else {
            intent.putExtra("NovoProduto", produto);
            setResult(RESULT_CODE_NOVO_PRODUTO, intent);
        }

        finish();

    }

}
