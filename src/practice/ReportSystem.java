package practice;
// Интерфейс для отчетов
interface IReport {
    String generate();
}

// Базовые классы отчетов
class SalesReport implements IReport {
    @Override
    public String generate() {
        return "Отчет по продажам: данные о продажах";
    }
}

class UserReport implements IReport {
    @Override
    public String generate() {
        return "Отчет по пользователям: данные о пользователях";
    }
}

// Абстрактный декоратор
abstract class ReportDecorator implements IReport {
    protected IReport report;

    public ReportDecorator(IReport report) {
        this.report = report;
    }

    @Override
    public String generate() {
        return report.generate();
    }
}

// Декоратор для фильтрации по датам
class DateFilterDecorator extends ReportDecorator {
    private String startDate;
    private String endDate;

    public DateFilterDecorator(IReport report, String startDate, String endDate) {
        super(report);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String generate() {
        return super.generate() + "\nФильтр по датам: с " + startDate + " по " + endDate;
    }
}

// Декоратор для сортировки данных
class SortingDecorator extends ReportDecorator {
    private String criteria;

    public SortingDecorator(IReport report, String criteria) {
        super(report);
        this.criteria = criteria;
    }

    @Override
    public String generate() {
        return super.generate() + "\nСортировка по: " + criteria;
    }
}

// Декораторы для экспорта
class CsvExportDecorator extends ReportDecorator {
    public CsvExportDecorator(IReport report) {
        super(report);
    }

    @Override
    public String generate() {
        return super.generate() + "\nЭкспорт в формат CSV.";
    }
}

class PdfExportDecorator extends ReportDecorator {
    public PdfExportDecorator(IReport report) {
        super(report);
    }

    @Override
    public String generate() {
        return super.generate() + "\nЭкспорт в формат PDF.";
    }
}

// Клиентский код
public class ReportSystem {
    public static void main(String[] args) {
        IReport report = new SalesReport();
        report = new DateFilterDecorator(report, "01-01-2024", "31-01-2024");
        report = new SortingDecorator(report, "дате");
        report = new CsvExportDecorator(report);

        System.out.println(report.generate());
    }
}