package crudwithvaadin.cruduiwithvaadin.web.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import crudwithvaadin.cruduiwithvaadin.web.domain.Customer;
import crudwithvaadin.cruduiwithvaadin.web.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tangyajun
 * @Description TO DO
 * @create 2020-04-14-20:02
 **/
@SpringComponent
@UIScope
public class CustomerEditor extends FormLayout implements KeyNotifier {
	private final CustomerRepository repository;

	/**
	 * 当前编辑的客户
	 */
	private Customer customer;

	/**
	 * 用于编辑客户实体的属性字段
	 */
	TextField name = new TextField(" name");
	TextField gender = new TextField("gender");
	TextField age=new TextField("age");
	TextField phone=new TextField("");
	TextField email=new TextField("email");
	TextField address=new TextField("address");

	/**
	 * 添加按钮
	 */
	Button save = new Button("保存", VaadinIcon.CHECK.create());
	Button cancel = new Button("取消",VaadinIcon.CALC.create());
	Button delete = new Button("删除", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<Customer> binder = new Binder<>(Customer.class);
	private ChangeHandler changeHandler;

	@Autowired
	public CustomerEditor(CustomerRepository repository) {
		this.repository = repository;
		binder.forField(age).withConverter(new StringToIntegerConverter(""))
		.bind(Customer::getAge,Customer::setAge);
		binder.forField(gender).withConverter(new StringToIntegerConverter(""))
		.bind(Customer::getGender,Customer::setGender);
		add(name, gender,age,phone,email,address, actions);

		// 绑定实例属性
		binder.bindInstanceFields(this);

		save.getElement().getThemeList().add("primary");
		delete.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> save());
		// 添加按钮单机监听事件
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editCustomer(customer));
		// 保存后设置为不可见
		setVisible(false);
	}

	void delete() {
		repository.delete(customer);
		changeHandler.onChange();
	}

	void save() {
		repository.save(customer);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editCustomer(Customer c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			customer = repository.findById(c.getId()).get();
		}
		else {
			customer = c;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(customer);

		setVisible(true);

		// Focus first name initially
		name.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}
}
