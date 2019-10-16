package backend.model;

import backend.model.enums.Category;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Search {

    public List<Menu> search(List<Service> services, List<Menu> menus, String menuName, Category menuCategory, String serviceTown) {
        List<Menu> allMenus;

        if (!serviceTown.isEmpty())
            allMenus = getMenusByServiceTown(services, serviceTown);
        else
            allMenus = menus;

        if (!menuName.isEmpty())
            allMenus = allMenus.stream().filter(m -> m.getName().contains(menuName)).collect(Collectors.toList());
        if (menuCategory != Category.All)
            allMenus = allMenus.stream().filter(m -> m.getCategory().equals(menuCategory)).collect(Collectors.toList());

        return allMenus;
    }

    private List<Menu> getMenusByServiceTown(List<Service> services, String serviceTown) {
        return services.stream().filter(s -> s.getAddress().getTown().equals(serviceTown)).map(Service::getValidMenus).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
