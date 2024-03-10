package g313.vakulenko.hw3_3_lms;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView equationOne, equationTwo, equationThree;
    private EditText solvingOne, solvingTwo, solvingThree;
    private int[] equationValue;
    private boolean right = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Привязка компонентов интерфейса к переменным
        equationOne = findViewById(R.id.equationOne);
        equationTwo = findViewById(R.id.equationTwo);
        equationThree = findViewById(R.id.equationThree);
        solvingOne = findViewById(R.id.solvingOne);
        solvingTwo = findViewById(R.id.solvingTwo);
        solvingThree = findViewById(R.id.solvingThree);

        // Генерация и отображение случайных примеров
        equationValue = valueArrayRandom();
        equationOne.setText(equationValue[0] + " + " + equationValue[1] + " = ");
        equationTwo.setText(equationValue[2] + " + " + equationValue[3] + " = ");
        equationThree.setText(equationValue[4] + " + " + equationValue[5] + " = ");

        // Установка обработчика нажатия клавиши Enter на полях ввода
        solvingOne.setOnKeyListener(onEnterKeyListener);
        solvingTwo.setOnKeyListener(onEnterKeyListener);
        solvingThree.setOnKeyListener(onEnterKeyListener);

        // Установка обработчика нажатия кнопки "Обновить примеры"
        Button butUpdate = findViewById(R.id.butUpdate);
        butUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEquations();
            }
        });
    }

    private View.OnKeyListener onEnterKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                EditText editText = (EditText) v;
                checkAnswer(editText);
                return true;
            }
            return false;
        }
    };

    // Метод для проверки ответа пользователя
    private void checkAnswer(EditText editText) {
        if (!editText.isEnabled()) {
            // Пользователь уже решил этот пример
            Toast.makeText(MainActivity.this, "Ты уже решил этот пример", Toast.LENGTH_SHORT).show();
            editText.setBackgroundColor(Color.TRANSPARENT);
            return;
        }

        // Получение введенного ответа
        String input = editText.getText().toString().trim(); // Удаление лишних пробелов

        // Проверка на пустой ввод
        if (input.isEmpty()) {
            Toast.makeText(MainActivity.this, "Пожалуйста, введите ответ", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int answer = Integer.parseInt(input);
            int expectedAnswer = 0;
            // Определение ожидаемого ответа в зависимости от EditText
            if (editText.getId() == R.id.solvingOne) {
                expectedAnswer = equationValue[0] + equationValue[1];
            } else if (editText.getId() == R.id.solvingTwo) {
                expectedAnswer = equationValue[2] + equationValue[3];
            } else if (editText.getId() == R.id.solvingThree) {
                expectedAnswer = equationValue[4] + equationValue[5];
            }

            // Проверка правильности ответа
            if (answer == expectedAnswer) {
                editText.setBackgroundColor(Color.GREEN);
                right = true;
            } else {
                editText.setBackgroundColor(Color.RED);
                right = false;
            }

            editText.setEnabled(false);
        } catch (NumberFormatException e) {
            // Обработка исключения, если введенное значение не является числом
            Toast.makeText(MainActivity.this, "Введите корректное число", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для обновления примеров и полей ввода
    private void updateEquations() {
        // Генерация новых случайных примеров
        equationValue = valueArrayRandom();

        // Обновление текста примеров
        equationOne.setText(equationValue[0] + " + " + equationValue[1] + " = ");
        equationTwo.setText(equationValue[2] + " + " + equationValue[3] + " = ");
        equationThree.setText(equationValue[4] + " + " + equationValue[5] + " = ");

        // Разблокировка полей ввода и сброс цвета фона
        solvingOne.setEnabled(true);
        solvingTwo.setEnabled(true);
        solvingThree.setEnabled(true);

        solvingOne.setText("");
        solvingTwo.setText("");
        solvingThree.setText("");

        solvingOne.setBackgroundColor(Color.TRANSPARENT);
        solvingTwo.setBackgroundColor(Color.TRANSPARENT);
        solvingThree.setBackgroundColor(Color.TRANSPARENT);
    }

    // Метод для генерации массива случайных чисел
    private int[] valueArrayRandom() {
        Random random = new Random();
        int[] arrayValue = new int[6];
        for (int i = 0; i < arrayValue.length; i++) {
            arrayValue[i] = random.nextInt(99) + 1;
        }
        return arrayValue;
    }
}
