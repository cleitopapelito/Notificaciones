package py.com.cleito.notificaciones;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    private static final int NOTIF_ALERTA_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnToast = (Button) findViewById(R.id.toast);
        Button btnDialog = (Button) findViewById(R.id.dialog);
        Button btnAlert = (Button) findViewById(R.id.alert);
        Button btnNotif = (Button) findViewById(R.id.notif);

        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "Mensaje tipo Toast", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            }
        });

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /***
                 * La clase AlertDialog contiene otra clase llamada Builder
                 * que es la que encapsula la creación de objetos de la clase AlertDialog.
                 * Procedemos a definir y crear un objeto de la clase Builder contenida en la clase AlertDialog:
                 * */
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                /***
                 * Una vez creado el objeto procedemos a llamar al método
                 * que define el título a mostrar en el diálogo.
                 * */

                builder.setTitle("Información");

                /***
                 * El mensaje propiamente dicho lo configuramos llamando al método setMessage
                 * */
                builder.setMessage("Este es un Diálogo del tipo alerta.");

                /***
                 * Mostramos un botón para poder cerrar el diálogo.
                 * */

                builder.setPositiveButton("OK",null);

                /***
                 * Finalmente llamamos al método que crea el diálogo y que el lo visualiza.
                 * */
                builder.create();
                builder.show();

            }
        });

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("Importante");
                dialog.setMessage("¿Acepta la ejecución de este programa en modo prueba?");
                dialog.setCancelable(false);

                dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aceptar();
                    }
                });

                dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancelar();
                    }
                });

                dialog.show();
            }
        });

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos una referencia al servicio de notificaciónes.
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager notManager = (NotificationManager) getSystemService(ns);

                //Configuramos la notificación
                //Es el mensaje que visualizamos sin abrir la barra de tareas
                int icono = android.R.drawable.stat_sys_warning;
                CharSequence textoEstado = "¡Notificación Alerta!";
                long hora = System.currentTimeMillis();

                Notification notif = new Notification(icono, textoEstado, hora);

                //Configuramos el Intent
                Context context = getApplicationContext();
                CharSequence titulo = "Mensaje de Alerta.";
                CharSequence descripcion = "Esto es una notificación.";

                Intent notIntent = new Intent(context, MainActivity.class);

                PendingIntent contIntent = PendingIntent.getActivity(context, 0, notIntent, 0);

                notif.setLatestEventInfo(context, titulo, descripcion, contIntent);

                //Autocancel; cuando se pulsa la notificación ésta desaparece.
                notif.flags |= Notification.FLAG_AUTO_CANCEL;

                //Añadir sonido, vibración y luces.
                notif.defaults |= Notification.DEFAULT_SOUND;
                notif.defaults |= Notification.DEFAULT_VIBRATE;
                notif.defaults |= Notification.DEFAULT_LIGHTS;

                //Enviar notificación
                notManager.notify(NOTIF_ALERTA_ID, notif);

            }
        });
    }

    public void aceptar(){
        Toast.makeText(MainActivity.this, "Bienvenido a probar el sistema :D", Toast.LENGTH_SHORT).show();
    }

    public void cancelar(){
        Toast.makeText(MainActivity.this, "No quería luego que pruebes mi sistema. >=[", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
