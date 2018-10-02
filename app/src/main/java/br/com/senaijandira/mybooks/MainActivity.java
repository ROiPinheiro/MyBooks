package br.com.senaijandira.mybooks;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.senaijandira.mybooks.fragments.LivrosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLidosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosParaLerFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        fragment_livros();
                        break;
                    case 1:
                        fragment_paraLer();
                        break;
                    case 2:
                        fragment_lidos();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void fragment_livros(){
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_content, new LivrosFragment());
        ft.commit();
    }

    public void fragment_paraLer(){
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_content, new LivrosParaLerFragment());
        ft.commit();
    }

    public void fragment_lidos(){
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_content, new LivrosLidosFragment());
        ft.commit();
    }
}
