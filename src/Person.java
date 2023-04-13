import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
public class Person extends JFrame {
        final JLabel surname_label = new JLabel("Фамилия");
        final JLabel name_label = new JLabel("Имя");
        final JLabel patronymic_label = new JLabel("Отчество");
        final JLabel date_label = new JLabel("Дата рождения");
        final JTextField surname_text = new JTextField("Иванов", 12);
        final JTextField name_text = new JTextField("Иван",12);
        final JTextField patronymic_text = new JTextField("Иванович",12);
        final JButton ready = new JButton("Готово");
        final JLabel person = new JLabel ("");
        public static void main(String[] args){
            new Person();
        }
        public Person(){
            final JPanel input = new JPanel();
            final JPanel surname = new JPanel();
            surname.setLayout(new GridLayout(0,1));
            surname.add(surname_label);
            surname.add(surname_text);
            final JPanel name = new JPanel();
            name.setLayout(new GridLayout(0,1));
            name.add(name_label);
            name.add(name_text);
            final JPanel patronymic = new JPanel();
            patronymic.setLayout(new GridLayout(0,1));
            patronymic.add(patronymic_label);
            patronymic.add(patronymic_text);
            final JPanel date = new JPanel();
            date.setLayout(new GridLayout(0,1));
            date.add(date_label);
            person.setFont(person.getFont().deriveFont(20f));
            final JFormattedTextField date_text = new JFormattedTextField("01.01.1970");
            try {
                date_text.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##.##.####")));
            } catch (Exception e) {}
            JTextField[] FIO = {surname_text, name_text, patronymic_text};
            ready.addActionListener(
                    new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            for (int i = 0; i < 3; i++){
                                if (FIO[i].getText().matches("[А-Я][а-я-]*$")){
                                    FIO[i].setBackground(Color.GREEN);
                                } else {
                                    FIO[i].setBackground(Color.RED);
                                    return;
                                }
                            }
                            String[] date_array = date_text.getText().split(".");
                            try {
                                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                                df.setLenient(false);
                                Date born = df.parse(date_text.getText());
                                if (born.after(new Date())) {
                                    date_text.setBackground(Color.RED);
                                    return;
                                }
                                char gender = is_gender(patronymic_text.getText());
                                int age = is_age(born);
                                String years = is_years(age);
                                person.setText(String.format("%s %c.%c.   %c   %d %s",
                                        FIO[0].getText(), FIO[1].getText().charAt(0), FIO[2].getText().charAt(0),
                                        gender, age, years));
                                date_text.setBackground(Color.GREEN);
                            } catch (ParseException exception) {
                                date_text.setBackground(Color.RED);
                            }
                        }
                    }
            );
            date.add(date_text);
            input.add(surname);
            input.add(name);
            input.add(patronymic);
            input.add(date);
            input.add(ready);
            getContentPane().add(input);
            person.setSize(500,10);
            setLocationRelativeTo(null);
            getContentPane().add(person);
            getContentPane().setLayout(new FlowLayout());
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(600,200);
            setLocationRelativeTo(null);
            setVisible(true);
        }
        char is_gender(String patronymic){
            char end = patronymic.charAt(patronymic.length()-1);
            if (end == 'ч') {
                return 'М';
            }
            if (end == 'а') {
                return 'Ж';
            }
            return '-';
        }
        int is_age(Date born){
            Date now = new Date();
            int age = now.getYear() - born.getYear();
            if (now.getMonth() > born.getMonth()){
                return age;
            }
            if (now.getMonth() < born.getMonth()){
                return age-1;
            }
            if (now.getDay() > born.getDay()){
                return age;
            }
            return age-1;
        }
        String is_years(int age){
            if (age % 10 == 1 & age % 100 != 11){
                return "год";
            }
            if ((age % 10 == 2 & age % 100 != 12) |
                    (age % 10 == 3 & age % 100 != 13) |
                    (age % 10 == 4 & age % 100 != 14)){
                return "года";
            }
            return "лет";
        }
}

