package backend.service;

import backend.controller.configuration.emailTemplateDTOs.MenuPurchase;
import backend.controller.configuration.emailTemplateDTOs.UserPurchases;
import backend.model.*;
import backend.repository.IPurchaseRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JobService {

    @Autowired
    private IPurchaseRepository purchaseRepository;
    @Autowired
    private CommunicationService communicationService;

    public void sendCustomersPurchasesEmail() throws MessagingException {
        communicationService.sendDailyPurchases("Your daily purchases summary from Viendas Ya", getCustomerPurchases(), false);
    }

    public void sendSuppliersSalesEmail() throws MessagingException {
        communicationService.sendDailyPurchases("Your daily sales summary from Viendas Ya", getSupplierPurchases(), true);
    }

    /* Private Methods */

    private List<UserPurchases> getCustomerPurchases() {
        LocalDate today = LocalDate.now();
        Map<User, List<Purchase>> purchasesPerCustomer = StreamSupport.stream(purchaseRepository.findAll().spliterator(), false).filter(p -> p.getPurchasedDate().equals(today)).collect(Collectors.groupingBy(Purchase::getCustomer));
        return getPurchasesMappedByMenu(purchasesPerCustomer, today);
    }

    private List<UserPurchases> getSupplierPurchases() {
        LocalDate today = LocalDate.now();
        Map<User, List<Purchase>> purchasesPerCustomer = StreamSupport.stream(purchaseRepository.findAll().spliterator(), false).filter(p -> p.getPurchasedDate().equals(today)).collect(Collectors.groupingBy(p -> p.getService().getSupplier()));
        return getPurchasesMappedByMenu(purchasesPerCustomer, today);
    }

    private List<UserPurchases> getPurchasesMappedByMenu(Map<User, List<Purchase>> purchasesPerCustomer, LocalDate today) {
        List<UserPurchases> dailyPurchases = new ArrayList<>();

        purchasesPerCustomer.forEach((user, purchases) -> {
            Map<Menu, List<Purchase>> purchasesPerMenu = purchases.stream().collect(Collectors.groupingBy(Purchase::getPurchasedMenu));
            List<MenuPurchase> menuPurchases = new ArrayList<>();

            purchasesPerMenu.forEach((menu, purchasesForEachMenu) -> {
                int totalMenuPurchasedAmount = purchasesForEachMenu.stream().reduce(0, (totalAmount, purchase2) -> totalAmount + purchase2.getPurchaseAmount(), Integer::sum);
                menuPurchases.add(new MenuPurchase(menu.getName(), (totalMenuPurchasedAmount / menu.getPrice()), totalMenuPurchasedAmount));
            });

            int totalAmount = menuPurchases.stream().reduce(0, (amount, nextMenuPurchase) -> amount + nextMenuPurchase.getPurchaseAmount(), Integer::sum);
            dailyPurchases.add(new UserPurchases(user, today, totalAmount, menuPurchases));
        });

        return dailyPurchases;
    }
}
