package pe.com.moviles.basicfirebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference BDReferencia;
    private ChildEventListener childEventListener;

    private TextView tvCurso, tvNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCurso = findViewById(R.id.tvCurso);
        tvNota = findViewById(R.id.tvNota);

        BDReferencia = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    switch (dataSnapshot.getKey()) {
                        case "Nota":
                            tvNota.setText("Agregado: " + dataSnapshot.getValue());
                            break;
                        case "Curso":
                            tvCurso.setText("Agregado: " + dataSnapshot.getValue());
                            break;
                    }
                } catch (NullPointerException nex) {
                    tvNota.setText("Error al agregar");
                    tvCurso.setText("Error al agregar");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    switch (dataSnapshot.getKey()) {
                        case "Nota":
                            tvNota.setText("Cambió: " + dataSnapshot.getValue());
                            break;
                        case "Curso":
                            tvCurso.setText("Cambió: " + dataSnapshot.getValue());
                            break;
                    }
                } catch (NullPointerException nex) {
                    tvNota.setText("Error al cambiar");
                    tvCurso.setText("Error al cambiar");
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                try {
                    switch (dataSnapshot.getKey()) {
                        case "Nota":
                            tvNota.setText("Eliminado");
                            break;
                        case "Curso":
                            tvCurso.setText("Eliminado");
                            break;
                    }
                } catch (NullPointerException nex) {
                    tvNota.setText("Error al eliminar");
                    tvCurso.setText("Error al eliminar");
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        BDReferencia.addChildEventListener(childEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (childEventListener != null)
            if (BDReferencia != null)
                BDReferencia.removeEventListener(childEventListener);
    }
}
