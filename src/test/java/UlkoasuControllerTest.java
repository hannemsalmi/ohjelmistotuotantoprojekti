package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JButton;

import org.junit.Test;

import view.UlkoasuController;
import view.ViewHandler;

public class UlkoasuControllerTest {
	
	@Test
	public void testClickEtusivu() throws Exception {
	    ViewHandler mockHandler = mock(ViewHandler.class);

	    UlkoasuController controller = new UlkoasuController();
	    controller.init(mockHandler);

	    Method method = UlkoasuController.class.getDeclaredMethod("clickEtusivu");
	    method.setAccessible(true);

	    method.invoke(controller);

	    verify(mockHandler).naytaEtusivu();
	}
	
	@Test
	public void testButtonClickKulut() throws Exception {
	    UlkoasuController controller = new UlkoasuController();
	    ViewHandler mockHandler = mock(ViewHandler.class);
	    controller.init(mockHandler);

	    Method method = UlkoasuController.class.getDeclaredMethod("clickKulut");
	    method.setAccessible(true);
	    method.invoke(controller);

	    verify(mockHandler).naytaKulut();
	}

	@Test
	public void testButtonClickDiagrammi() throws Exception {
	    UlkoasuController controller = new UlkoasuController();
	    ViewHandler mockHandler = mock(ViewHandler.class);
	    controller.init(mockHandler);

	    Method method = UlkoasuController.class.getDeclaredMethod("clickDiagrammi");
	    method.setAccessible(true);
	    method.invoke(controller);

	    verify(mockHandler).naytaDiagrammi();
	}

	@Test
	public void testButtonClickEnnuste() throws Exception {
	    UlkoasuController controller = new UlkoasuController();
	    ViewHandler mockHandler = mock(ViewHandler.class);
	    controller.init(mockHandler);

	    Method method = UlkoasuController.class.getDeclaredMethod("clickEnnuste");
	    method.setAccessible(true);
	    method.invoke(controller);

	    verify(mockHandler).naytaEnnuste();
	}

	@Test
	public void testButtonClickAsetukset() throws Exception {
	    UlkoasuController controller = new UlkoasuController();
	    ViewHandler mockHandler = mock(ViewHandler.class);
	    controller.init(mockHandler);

	    Method method = UlkoasuController.class.getDeclaredMethod("clickAsetukset");
	    method.setAccessible(true);
	    method.invoke(controller);

	    verify(mockHandler).naytaAsetukset();
	}
}