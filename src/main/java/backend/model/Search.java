package backend.model;

import backend.model.enums.Category;
import backend.repository.UnityOfWork;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Search {

    public static List<Menu> search(UnityOfWork unityOfWork, String menuName, Category menuCategory, String serviceTown) {
        List<Menu> allMenus;

        if (!serviceTown.isEmpty())
            allMenus = getMenusByServiceTown(unityOfWork, serviceTown);
        else
            allMenus = unityOfWork.getAllMenus();

        if (!menuName.isEmpty())
            allMenus = allMenus.stream().filter(m -> m.getName().contains(menuName)).collect(Collectors.toList());
        if (menuCategory != null)
            allMenus = allMenus.stream().filter(m -> m.getCategory().equals(menuCategory)).collect(Collectors.toList());

        return allMenus;
    }

    private static List<Menu> getMenusByServiceTown(UnityOfWork unityOfWork, String serviceTown) {
        List<Service> allServices = unityOfWork.getAllServices();
        return allServices.stream().filter(s -> s.getAddress().getTown().equals(serviceTown)).map(Service::getMenus).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
