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
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WebController {

    @RequestMapping("/")
    public String index() {
        return "startPage";
    }

    @RequestMapping(value = "/getTopic", method = RequestMethod.POST)
    public String getTopic(@RequestParam("topic") String topic, HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        System.out.println("topic = " + topic);
        model.addAttribute("topic",topic);
        return "hello";
    }

    @RequestMapping(value = "/ajaxTest", method = RequestMethod.POST)
    public @ResponseBody String getCharNum(@RequestParam("topic") String topic) throws Exception {
        System.out.println("this is from controller: " + topic);
        return topic;
    }
}
