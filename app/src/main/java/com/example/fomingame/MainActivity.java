package com.example.fomingame;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class MainActivity extends AppCompatActivity {
    Character player; // персонаж
    Story story; // история (сюжет)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // создаем нового персонажа и историю
        Statistics initials = new Statistics(100, 0, 0, 1);
        player = new Character("Вася", initials);
        ((TextView)findViewById(R.id.name)).setText(String.format("%s - %d дней.", player.name, player.life));

        Situation start = new Situation("Первый квест!", "", "Это начало приключения. Ты нашёл качалку.", new Statistics());
        start.options.add(new Situation("Качаться", "Ты станешь сильнее.", "Твоя сила увеличилась!", new Statistics(0, 0, 0, 2)));
        start.options.add(new Situation("Пройти мимо", "", "Ты ничего не сделал.", new Statistics(0, 0, 0, 0)));
        story = new Story(player, start);

        Situation quest2 = new Battle("Волк", "Жители вам благодарны.", new Statistics(0, 0, 5, 0), 2);
        story.addQuest(quest2);

        // в первый раз выводим на форму весь необходимый текст и элементы
        // управления
        updateStatus();
    }

    // метод для перехода на нужную ветку развития
    private void go() {
        story.doQuest();

        // не забываем обновить репутацию в соответствии с новым
        // состоянием дел
        updateStatus();
        // если история закончилась, выводим на экран поздравление
        if (story.isEnd())
            Toast.makeText(this, "Игра закончена!", Toast.LENGTH_LONG).show();
    }

    // в этом методе размещаем всю информацию, специфичную для текущей
    // ситуации на форме приложения, а также размещаем кнопки, которые
    // позволят пользователю выбрать дальнейший ход событий
    private void updateStatus() {
        // выводим статус на форму
        ((TextView) findViewById(R.id.status)).
                setText(String.format("Деньги: %d\nВласть: %d\nРепутация: %d\nСила: %d", player.stat.money, player.stat.power, player.stat.reputation, player.stat.strength));

        Situation current = story.getCurrentSituation();
        // аналогично для заголовка и описания ситуации
        ((TextView) findViewById(R.id.title)).
                setText(current.title);
        ((TextView) findViewById(R.id.result)).
                setText(current.result);
        // стереть все старые кнопки
        ((LinearLayout) findViewById(R.id.layout)).removeAllViews();

        if (current.options.size() == 0) {
            Button b = new Button(this);
            b.setText("Дальше");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    story.nextQuest();
                    go();
                }
            });
            // добавляем готовую кнопку на разметку
            ((LinearLayout) findViewById(R.id.layout)).addView(b);
        }
        else
            // размещаем кнопку для каждого варианта, который пользователь
            // может выбрать
            for (int i = 0; i < current.options.size(); i++) {
                Button b = new Button(this);
                Situation sit = current.options.get(i);
                b.setText(String.format("%s\n%s", sit.title, sit.description));

                final int optionId = i;
                // Внимание! в анонимных классах
                // можно использовать только те переменные метода,
                // которые объявлены как final.
                // Создаем объект анонимного класса и устанавливаем его
                // обработчиком нажатия на кнопку
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        story.choose(optionId);
                        go();
                        // поскольку анонимный класс имеет полный
                        // доступ к методам и переменным родительского,
                        // то просто вызываем нужный нам метод.
                    }
                });
                // добавляем готовую кнопку на разметку
                ((LinearLayout) findViewById(R.id.layout)).addView(b);
            }
    }

}