package com.example.fomingame;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

        story = new Story(player);
        Situation.story = story;
        ArrayList<Situation> quests = new ArrayList<>();

        Situation quest = new Situation("Первый квест!", "Это начало приключения. Ты нашёл качалку.");
        quest.options.add(new Situation("Качаться", "Ты станешь сильнее.", "Твоя сила увеличилась!", new Statistics(0, 0, 0, 2)));
        quest.options.add(new Situation("Пройти мимо", "Ты ничего не сделал."));
        quests.add(quest);

        quests.add(new Battle("Волк", "Жители вам благодарны.", new Statistics(0, 0, 5, 0), 2));

        quest = new Situation("Золото", "Вы нашли сундук с золотом. Что сделать с ним?");
        quest.options.add(new Situation("Отдать крестьянам", "", "Ты раздал деньги нуждающимся. Люди рады.", new Statistics(0, 0, 10, 0)));
        quest.options.add(new Situation("Забрать", "", "Ты оставил золото себе.", new Statistics(500, 0, 0, 0)));
        quests.add(quest);

        quests.add(new Situation("Наступает ночь", "Стемнело"));
        quests.add(new Situation("Вы услышали звук", "Кажется, что-то шевелится в кустах."));

        quest = new Situation("Незнакомка", "Из леса вышла девушка в порваном платье.");
        Situation quest1 = new Situation("Поцеловать", "Вы подходите и обнимаете даму, но вдруг у неё появляются " +
                "клыки и она становится оборотнем!");
        quest1.options.add(new Battle("Оборотень", "Мёртвая туша оборотня сильно воняет", new Statistics(0, 0, 0, 1), 2));
        quest.options.add(quest1);
        quest.options.add(new Situation("Убить", "Вы быстро подбегаете к девушке и вонзаете свой меч в её тело. " +
                "Труп странно пахнет. Зачем вы это сделали?"));
        quest.options.add(new Situation("Стесняться", "Вы убежали от девушки ;)"));
        quests.add(quest);

        for (Situation q : quests) {
            story.addQuest(q, true);
        }

        // в первый раз выводим на форму весь необходимый текст и элементы
        // управления
        updateStatus();
    }

    // метод для перехода на нужную ветку развития
    private void go() {
        // если история закончилась, выводим на экран поздравление
        if (story.isEnd()) {
            Toast.makeText(this, "Игра закончена!", Toast.LENGTH_LONG).show();
            return;
        }

        story.doQuest();

        // не забываем обновить репутацию в соответствии с новым
        // состоянием дел
        updateStatus();
    }

    // в этом методе размещаем всю информацию, специфичную для текущей
    // ситуации на форме приложения, а также размещаем кнопки, которые
    // позволят пользователю выбрать дальнейший ход событий
    private void updateStatus() {
        // выводим статус на форму
        ((TextView)findViewById(R.id.name)).setText(String.format("%s", player.name));
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