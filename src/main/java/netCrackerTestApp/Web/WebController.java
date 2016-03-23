//package netCrackerTestApp.Web;
//
//import org.apache.log4j.Logger;
//import org.springframework.boot.context.web.SpringBootServletInitializer;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
////@EnableAutoConfiguration
//public class WebController extends SpringBootServletInitializer {
//
////    @RequestMapping(value = "index")
//////    @ResponseBody
////    public String home() {
////        return "index";
////    }
//
//    static Logger logger = Logger.getLogger(WebController.class);
//
//    @RequestMapping("/lol")
//    public ModelAndView defaultAdminRequest(final HttpServletRequest request) {
//        logger.info("lol request");
//        return  new ModelAndView("jsp_test");
//    }
//}
package netCrackerTestApp.Web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WebController {

    @RequestMapping
            (value = "", method = RequestMethod.GET)
    public ModelAndView emptyPath() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("startPage");
        return mav;
    }

    @RequestMapping
            (value = "/main", method = RequestMethod.GET)
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("startPage");
        return mav;
    }

    @RequestMapping
            (value = "/RequestServlet", method = RequestMethod.POST)
    public ModelAndView request(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestServlet requestServlet = new RequestServlet();
        requestServlet.processRequest(request, response);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("startPage");
        return mav;
    }
}
