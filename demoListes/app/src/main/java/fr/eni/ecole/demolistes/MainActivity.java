package fr.eni.ecole.demolistes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import fr.eni.ecole.demolistes.adapter.PersonneAdapter;
import fr.eni.ecole.demolistes.adapter.PersonneRecyclerAdapter;
import fr.eni.ecole.demolistes.bo.Personne;
import fr.eni.ecole.demolistes.manager.PersonneManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_main);

        PersonneRecyclerAdapter adapter
                = new PersonneRecyclerAdapter(PersonneManager.getList(),
                    new PersonneRecyclerAdapter.onClickItemListener() {
                        @Override
                        public void onClickItem(Personne p) {
                            Toast.makeText(MainActivity.this,
                                    p.getPrenom() + " " + p.getNom(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
        RecyclerView recycler = findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        /*
        setContentView(R.layout.activity_main);

        ListView liste = findViewById(R.id.lstviewPersonnes);

        PersonneAdapter adapter = new PersonneAdapter(MainActivity.this,
                                R.layout.layout_item,
                                PersonneManager.getList());

        liste.setAdapter(adapter);

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Personne p = (Personne) parent.getItemAtPosition(position);

                Toast.makeText(MainActivity.this,
                        p.getPrenom() + " " + p.getNom(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
        */

    }
}
