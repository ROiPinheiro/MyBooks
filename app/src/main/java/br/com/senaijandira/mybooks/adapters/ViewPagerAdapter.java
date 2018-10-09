package br.com.senaijandira.mybooks.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.senaijandira.mybooks.fragments.LivrosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosLidosFragment;
import br.com.senaijandira.mybooks.fragments.LivrosParaLerFragment;

//Classe para controler da animação de swipe entre as fragments
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter (FragmentManager fragmentManager){
        super(fragmentManager);
    }

    //retorna a fragment dependendo do item do menu selecionado
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new LivrosFragment();
            case 1: return new LivrosParaLerFragment();
            case 2: return new LivrosLidosFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //seta os titulos dos menus
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0: return "Livros";
            case 1: return "Para Ler";
            case 2: return "Lidos";
            default: return null;
        }
    }

    //TODO https://looksok.wordpress.com/2013/11/02/viewpager-with-detailed-fragment-lifecycle-onresumefragment-including-source-code/
}
