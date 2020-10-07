package cn.fjl.test;


import cn.fjl.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/*
* 使用Spring测试模块提供的测试请求功能,测试CRUD请求的正确性
* Spring4,5测试的时候,需要servlet3.0的支持
* */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:springmvc.xml"})
public class MvcTest {
/*
* 传入Springmvc的ioc 因为WebApplicationContext是属于Springmvc的一个ioc容器
* 又因为@Autowired自动装配的注解只能自动装配容器里面的组件，而容器自己是无法直接自动装配的
* 所以要想实现这个功能，需要在开头使用@WebAppConfiguration注解就可以使用@Autowired获取容器本身了
* */
    @Autowired
    WebApplicationContext context;
//虚拟mvc请求,获取到处理结果
    MockMvc mockMvc;
/*
* 下面这个public void initMokcMvc()方法就是用来使用MockMvc来模拟发送请求进行测试，然后每次使用
* 要在方法前加    @Before注解进行初始化
* */
    @Before
    public void initMokcMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void testPage() throws Exception {
//        模拟请求拿到返回值
        /*
        * mockMvc中的perform方法就是用来模拟发送指定请求，需要传入requestBuilder，这个requestBuilder通过使用
        * MockMvcRequestBuilders方法来创建requestBuilder。
        * .后面自行选择请求方法，这里测试的是get请求，所以是MockMvcRequestBuilders.get
        * 传入要访问的请求即uri（"/emps"），然后请求携带的参数使用param方法传入name和value，这个方法只能携带一个参数
        * 如果需要携带多个参数，要使用params方法，传入Map类型数据
        * 然后在外面结尾使用andReturn方法即可取得结果数据
        * */
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5")).andReturn();
//        请求域中会有pageInfo（根据controller中的查询代码可知）:我们可以取出pageInfoj进行验证
        MockHttpServletRequest request = result.getRequest();
        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页码:"+pi.getPageNum());
        System.out.println("总页码:"+pi.getPages());
        System.out.println("总记录数:"+pi.getTotal());
        System.out.println("在页面需要连续显示的页码:");
        int[] nums = pi.getNavigatepageNums();
        for (int i : nums){
            System.out.print(" "+i);
        }

//        获取员工数据
        List<Employee> list = pi.getList();
        for (Employee employee : list){
            System.out.println("ID:"+employee.getEmpId()+"==>Name:"+employee.getEmpName());
        }
    }
}
