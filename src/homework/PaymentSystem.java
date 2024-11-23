package homework;
// Интерфейс существующей системы
interface IPaymentProcessor {
    void processPayment(double amount);
}

// Реализация PayPal
class PayPalPaymentProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Оплата через PayPal: $" + amount);
    }
}

// Сторонняя система Stripe
class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Транзакция через Stripe: $" + totalAmount);
    }
}

// Адаптер для Stripe
class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;

    public StripePaymentAdapter(StripePaymentService stripeService) {
        this.stripeService = stripeService;
    }

    @Override
    public void processPayment(double amount) {
        stripeService.makeTransaction(amount);
    }
}

// Еще один сторонний сервис (например, Square)
class SquarePaymentService {
    public void processSquarePayment(double amount) {
        System.out.println("Оплата через Square: $" + amount);
    }
}

// Адаптер для Square
class SquarePaymentAdapter implements IPaymentProcessor {
    private SquarePaymentService squareService;

    public SquarePaymentAdapter(SquarePaymentService squareService) {
        this.squareService = squareService;
    }

    @Override
    public void processPayment(double amount) {
        squareService.processSquarePayment(amount);
    }
}

// Клиентский код
public class PaymentSystem {
    public static void main(String[] args) {
        IPaymentProcessor paypalProcessor = new PayPalPaymentProcessor();
        paypalProcessor.processPayment(100.0);

        StripePaymentService stripeService = new StripePaymentService();
        IPaymentProcessor stripeProcessor = new StripePaymentAdapter(stripeService);
        stripeProcessor.processPayment(200.0);

        SquarePaymentService squareService = new SquarePaymentService();
        IPaymentProcessor squareProcessor = new SquarePaymentAdapter(squareService);
        squareProcessor.processPayment(300.0);
    }
}