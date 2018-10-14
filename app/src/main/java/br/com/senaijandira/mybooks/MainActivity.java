package br.com.senaijandira.mybooks;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.senaijandira.mybooks.fragments.LivrosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLidosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosParaLerFragment;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;

    Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //serve para deixar o nome do App junto com as tabs
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        abrirLivros();
                        break;
                    case 1:
                        abrirLer();
                        break;
                    case 2:
                        abrirLidos();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        fm = getSupportFragmentManager();

        abrirLivros();
    }


    public void abrirLivros() {

        //transição de troca entre fragments
        FragmentTransaction ft = fm.beginTransaction();

        //trocar o conteudo de uma layout
        ft.replace(R.id.container, new LivrosFragment());
        ft.commit();
    }

    public void abrirLer() {

        //transição de troca entre fragments
        FragmentTransaction ft = fm.beginTransaction();

        //trocar o conteudo de uma layout
        ft.replace(R.id.container, new LivrosParaLerFragment());
        ft.commit();
    }

    public void abrirLidos() {

        //transição de troca entre fragments
        FragmentTransaction ft = fm.beginTransaction();

        //trocar o conteudo de uma layout
        ft.replace(R.id.container, new LivrosLidosFragment());
        ft.commit();
    }

}
