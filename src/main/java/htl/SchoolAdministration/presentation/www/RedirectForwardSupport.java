package htl.SchoolAdministration.presentation.www;

public interface RedirectForwardSupport {

    default String redirect(String route) {
        //check on the route
        return "redirect:%s".formatted(route);
    }

    default  String forward(String route) {
        //check on the route
        return "forward:%s".formatted(route);
    }
}
