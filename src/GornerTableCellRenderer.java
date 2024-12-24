import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    // Ищем ячейки, строковое представление которых совпадает с needle
// (иголкой). Применяется аналогия поиска иголки в стоге сена, в роли
// стога сена - таблица
    private String needle = null;
    private DecimalFormat formatter =
            (DecimalFormat)NumberFormat.getInstance();
    public GornerTableCellRenderer() {
// Показывать только 5 знаков после запятой
        formatter.setMaximumFractionDigits(5);
// Не использовать группировку (т.е. не отделять тысячи
// ни запятыми, ни пробелами), т.е. показывать число как "1000",
// а не "1 000" или "1,000"
        formatter.setGroupingUsed(false);
// Установить в качестве разделителя дробной части точку, а не
// запятую. По умолчанию, в региональных настройках
// Россия/Беларусь дробная часть отделяется запятой
        DecimalFormatSymbols dottedDouble =
                formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
// Разместить надпись внутри панели
        panel.add(label);
// Установить выравнивание надписи по левому краю панели
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public  DecimalFormat getFormatter(){
        return formatter;}

    // НАХОЖДЕНИЕ ЗНАЧЕНИЯ МНОГОЧЛЕНА, ВВЕДЯ САМ РЕЗУЛЬТАТ ВЫЧИСЛЕНИЯ
//    public Component getTableCellRendererComponent(JTable table,
//                                                   Object value, boolean isSelected, boolean hasFocus, int row, int col) {
//// Преобразовать double в строку с помощью форматировщика
//        String formattedDouble = formatter.format(value);
//// Установить текст надписи равным строковому представлению числа
//        label.setText(formattedDouble);
//        if (col==1 && needle!=null && needle.equals(formattedDouble)) {
//// Номер столбца = 1 (т.е. второй столбец) + иголка не null
//// (значит что-то ищем) +
//// значение иголки совпадает со значением ячейки таблицы -
//// окрасить задний фон панели в красный цвет
//            panel.setBackground(Color.RED);
//        } else {
//// Иначе - в обычный белый
//            panel.setBackground(Color.WHITE);
//        }
//        return panel;
//    }

    //НАХОЖДЕНИЕ ЗНАЧЕНИЯ МНОГОЧЛЕНА, ВВЕДЯ СООТВЕТСТВУЮЩИЙ Х
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row, int col) {
// Преобразовать double в строку с помощью форматировщика
        String formattedDouble = formatter.format(value);
// Установить текст надписи равным строковому представлению числа
        label.setText(formattedDouble);
        Object xValue = table.getValueAt(row, 0);
        String formattedX = formatter.format(xValue);
        if (col == 1 && needle != null && needle.equals(formattedX)) {
            panel.setBackground(Color.RED); }
        else {
            panel.setBackground(Color.WHITE); }



        // Проверяем, что все символы строки являются 1, 3 или 5
        if (col == 1 ) {
            String formattedRemainder = formattedDouble.contains(".") ? formattedDouble.split("\\.")[1]: "";
            for (char part : formattedRemainder.toCharArray()) {
                if (part != '1' && part != '3' && part != '5') {
                    if (!panel.getBackground().equals(Color.RED))
                        panel.setBackground(Color.WHITE);
                    break;
                }
                else
                if (!panel.getBackground().equals(Color.RED))
                    panel.setBackground(Color.CYAN);
            }
        }
        return panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
        System.out.println("Needle set to: " + needle); // Отладочная информация
    }
}
