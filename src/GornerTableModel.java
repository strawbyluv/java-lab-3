import javax.swing.table.AbstractTableModel;
@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step,
                            Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
// В данной модели два столбца
        return 3;
    }

    public int getRowCount() {
// Вычислить количество точек между началом и концом отрезка
// исходя из шага табулирования
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
// Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step * row;
        if (col == 0) {
// Если запрашивается значение 1-го столбца, то это X
            return x;
        } else {
// Если запрашивается значение 2-го столбца, то это значение
// многочлена
            Double result = 0.0;
// Вычисление значения в точке по схеме Горнера.
            int k = coefficients.length - 1;
            for (int i = 0; i <= k; i++) {
                double prev = result;
                result = coefficients[i] + x * prev;
            }
            if (col == 1)
                return result;
            else
                return isPrime(result.doubleValue());
        }

    }

    private Boolean isPrime(double a) {
        int res = (int) (a - a % 1);
        if (res <= 1)
            return false;
        for (int i = 2; i < Math.sqrt(a)+1; i++) {
            if (res % i == 0)
                return false;
        }
        return true;
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
// Название 1-го столбца
                return "Значение X";
            case 1:
// Название 2-го столбца
                return "Значение многочлена";
            default:
// Название 3-го столбца
                return "Значение простое?";
        }
    }

    public Class<?> getColumnClass(int col) {
// И в 1-ом и во 2-ом столбце находятся значения типа Double
        if (col != 2)
            return Double.class;
        else
            return Boolean.class;
    }
}
