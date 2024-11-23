package practice;
// Интерфейс внутренней службы доставки
interface IInternalDeliveryService {
    void deliverOrder(String orderId);
    String getDeliveryStatus(String orderId);
}

// Реализация внутренней службы доставки
class InternalDeliveryService implements IInternalDeliveryService {
    @Override
    public void deliverOrder(String orderId) {
        System.out.println("Доставка заказа " + orderId + " внутренней службой начата.");
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return "Статус доставки заказа " + orderId + ": В процессе.";
    }
}

// Сторонняя служба A
class ExternalLogisticsServiceA {
    public void shipItem(int itemId) {
        System.out.println("Отправка товара " + itemId + " службой A.");
    }

    public String trackShipment(int shipmentId) {
        return "Статус отправления " + shipmentId + ": Доставляется службой A.";
    }
}

// Адаптер для службы A
class LogisticsAdapterA implements IInternalDeliveryService {
    private ExternalLogisticsServiceA externalService;

    public LogisticsAdapterA(ExternalLogisticsServiceA externalService) {
        this.externalService = externalService;
    }

    @Override
    public void deliverOrder(String orderId) {
        externalService.shipItem(Integer.parseInt(orderId));
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return externalService.trackShipment(Integer.parseInt(orderId));
    }
}

// Сторонняя служба B
class ExternalLogisticsServiceB {
    public void sendPackage(String packageInfo) {
        System.out.println("Отправка пакета: " + packageInfo + " службой B.");
    }

    public String checkPackageStatus(String trackingCode) {
        return "Статус пакета " + trackingCode + ": Доставляется службой B.";
    }
}

// Адаптер для службы B
class LogisticsAdapterB implements IInternalDeliveryService {
    private ExternalLogisticsServiceB externalService;

    public LogisticsAdapterB(ExternalLogisticsServiceB externalService) {
        this.externalService = externalService;
    }

    @Override
    public void deliverOrder(String orderId) {
        externalService.sendPackage("Заказ " + orderId);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return externalService.checkPackageStatus("Tracking-" + orderId);
    }
}

// Фабрика для выбора службы доставки
class DeliveryServiceFactory {
    public static IInternalDeliveryService getDeliveryService(String serviceType) {
        switch (serviceType) {
            case "internal":
                return new InternalDeliveryService();
            case "A":
                return new LogisticsAdapterA(new ExternalLogisticsServiceA());
            case "B":
                return new LogisticsAdapterB(new ExternalLogisticsServiceB());
            default:
                throw new IllegalArgumentException("Неизвестный тип службы доставки.");
        }
    }
}

// Клиентский код
public class LogisticsSystem {
    public static void main(String[] args) {
        IInternalDeliveryService deliveryService = DeliveryServiceFactory.getDeliveryService("A");
        deliveryService.deliverOrder("123");
        System.out.println(deliveryService.getDeliveryStatus("123"));

        deliveryService = DeliveryServiceFactory.getDeliveryService("B");
        deliveryService.deliverOrder("456");
        System.out.println(deliveryService.getDeliveryStatus("456"));
    }
}