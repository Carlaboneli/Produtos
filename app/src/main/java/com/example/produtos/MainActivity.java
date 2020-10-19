package com.example.produtos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.produtos.Modelo.Produto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_NOVO_PRODUTO = 1;
    private final int RESULT_CODE_NOVO_PRODUTO = 10;
    private final int REQUEST_CODE_EDITAR_PRODUTO = 2;
    private final int RESULT_CODE_PRODUTO_EDITADO = 11;

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");

        listViewProdutos = findViewById(R.id.listView_Produtos);
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                produtos);
        listViewProdutos.setAdapter(adapterProdutos);

        definirOnClicklistenerListView();


    }

    private void definirOnClicklistenerListView() {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Produto produtoClicado = adapterProdutos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
                intent.putExtra("produtoEdicao", produtoClicado);
                startActivityForResult(intent, REQUEST_CODE_EDITAR_PRODUTO);
            }
        });

    }


    public void onClickNovoproduto(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NOVO_PRODUTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_NOVO_PRODUTO && resultCode == RESULT_CODE_NOVO_PRODUTO) {
            Produto produto = (Produto) data.getExtras().getSerializable("NovoProduto");
            produto.setId(++id);
            this.adapterProdutos.add(produto);

        } else if (requestCode == REQUEST_CODE_EDITAR_PRODUTO && resultCode == RESULT_CODE_PRODUTO_EDITADO) {
            Produto produtoEditado = (Produto) data.getExtras().getSerializable("produtoEditado");
            for (int i = 0; i < adapterProdutos.getCount(); i++) {
                Produto produto = adapterProdutos.getItem(i);
                if (produto.getId() == produtoEditado.getId()) {
                    adapterProdutos.remove(produto);
                    adapterProdutos.insert(produtoEditado, i);
                    break;
                }

            }

            Toast.makeText(MainActivity.this, " Produto Editado ", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);

        listViewProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Produto produtoClicado = adapterProdutos.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon((android.R.drawable.ic_delete))
                        .setTitle("Deseja Excluir? ")
                        .setMessage("Deseja Ecluir este Produto ? ")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterProdutos.remove(produtoClicado);
                                adapterProdutos.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Produto deletado ", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Não", null).show();


                return true;
            }
        });

    }
}