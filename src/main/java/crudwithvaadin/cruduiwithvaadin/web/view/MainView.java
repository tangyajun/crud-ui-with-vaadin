package crudwithvaadin.cruduiwithvaadin.web.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import crudwithvaadin.cruduiwithvaadin.web.domain.Customer;
import crudwithvaadin.cruduiwithvaadin.web.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tangyajun
 * @Description TO DO
 * @create 2020-04-14-11:08
 **/
@Route
public class MainView extends VerticalLayout {

	private final CustomerRepository repo;

	/**
	 * 客户编辑视图
	 */
	private final CustomerEditor editor;
	/**
	 * 客户列表Grid
	 */
	final Grid<Customer> grid;

	/**
	 * 文本查询过滤
	 */
	final TextField filter;

	/**
	 * 新增按钮
	 */
	private final Button addNewBtn;

	public MainView(CustomerRepository repo, CustomerEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("新建", VaadinIcon.PLUS.create());

		// 设置文本查询和新增按钮布局
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		// 将组建添加到
		add(actions, grid, editor);
		// 设置grid高度
		grid.setHeight("300px");
		// 设置grid列名
		grid.setColumns("id", "name", "gender","age","phone","email","address");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
		// 设置文本占位符
		filter.setPlaceholder("请输入客户姓名");
		// 设置文本改变的模式
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		// 添加文本改变监听器
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		// 添加grid列选择事件
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		// 新建按钮添加单击监听
		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", 1,28,"15001272181","test@126.com","")));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		listCustomers(null);
	}

	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByNameStartingWithIgnoreCase(filterText));
		}
	}
}
