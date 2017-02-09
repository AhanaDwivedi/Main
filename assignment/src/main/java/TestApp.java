import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;  
import org.springframework.beans.factory.xml.XmlBeanFactory;  
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;  
import org.springframework.core.io.Resource;  

import com.sas.assignment.bean.MenuItem;
import com.sas.assignment.helper.CSVResourceLoader;
import com.sas.assignment.services.MenuCombinationServiceImpl;
  
public class TestApp {  
public static void main(String[] args) {  
    Resource resource=new ClassPathResource("applicationContext.xml");  
    BeanFactory factory=new XmlBeanFactory(resource);  
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    
    MenuCombinationServiceImpl menuCombSrvc = (MenuCombinationServiceImpl) context.getBean("menuCombinationService");
    
    Map<String,List<MenuItem>> outCombinationMap = menuCombSrvc.findBestCombination();
    
    for(String s: outCombinationMap.keySet()) {
    	System.out.println(System.lineSeparator() + "Option " + (Integer.parseInt(s) + 1)
    			+ "-----------------------------"
    			+ System.lineSeparator());
		for (MenuItem m : outCombinationMap.get(s)) {
			System.out.println("Item " + m.getItemName() + ":"
					+ m.getItemPrice());
		}
		
    }
}  
}  