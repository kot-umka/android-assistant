package com.skillbox.mike.assistant;

import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /**
     * Элемент в котором отображается чат
     */
    protected TextView chat;

    /**
     * Поле для ввода сообщение
     */
    protected EditText message;

    /**
     * Кнопка "отправить"
     */
    protected Button send;

    /**
     * Метод, который вызывается при создании Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Найдем нужные элементы интерфейса по id
         */
        chat = (TextView) findViewById(R.id.chat);
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);

        /**
         * Назначим обработчик события "Клик" на кнопке "отправить"
         */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = message.getText().toString(); // Получим текст вопроса
                message.setText(""); // Очистим поле
                chat.append("\n<< " + question); // Отобразим вопрос в чате
                String answer = answerQuestion(question); // Вычислим тексто ответа
                chat.append("\n>> " + answer); // Отобразим ответ в чате
            }
        });
    }

    protected String answerQuestion(String question) {
        question = question.toLowerCase(); // Привидем текст к нижнему регистру
        Map<String, String> questions = new HashMap<String, String>(){{ // Заполним "карту ответов"
            put("как дела", "Шикарно");
            put("чем занимаешься", "Отвечаю на дурацкие вопросы");
            put("как тебя зовут", "Меня зовут Ассистентий");
            put("лал", "кек");
            put("кек", "чебурек");
        }};

        List<String> result = new ArrayList<>(); // В этом списке будем хранить ответы

        for(String quest : questions.keySet()) { // Пройдем циклом по карте ответов и найдем совпадающие вопросы
            if (question.contains(quest)) {
                result.add(questions.get(quest)); // Если в тексте содержится вопрос, то запишем в список соотв. ответ
            }
        }
        if (question.contains("сколько времени")) { // Отдельно предусмотрим случай, когда пользователь спрашивает время
            DateFormat fmt = new SimpleDateFormat("HH:mm:ss"); // Создадим объект для форматирования даты/времени
            String time = fmt.format(new Date()); // Отформатируем текущую дату
            result.add("Сейчас " + time);  // Запишем ответ в список
        }

        return TextUtils.join(", ", result); // Все получившиеся ответы объединим через запятую и вернем как результат метода
    }
}
