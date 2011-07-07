package org.zero.utils.tests;

import static org.junit.Assert.assertEquals;

import org.junit.ComparisonFailure;
import org.junit.Test;
import org.zero.utils.JUnitUtils;

public class JUnitUtilsTests {
	
	@Test
	public void doAssertBlockEqualsTest() {
		StringBuilder actualBlock = new StringBuilder();
		
		actualBlock.append("linha 1\n");
		actualBlock.append("linha 2\n");
		actualBlock.append("linha 3\n");
		

		StringBuilder expectedBlock = new StringBuilder();
		
		expectedBlock.append("linha 1\n");
		expectedBlock.append("linha 2\n");
		expectedBlock.append("linha 3\n");
		
		// Teste autocontido, nessas condições deve passar
		JUnitUtils.assertBlockEquals("Comparação de blocos de código iguais", expectedBlock.toString(), actualBlock.toString());
	}

	@Test
	public void doAssertBlockNotEqualsTest() {
		StringBuilder actualBlock = new StringBuilder();
		
		actualBlock.append("linha 1\n");
		actualBlock.append("-- diferente --\n");
		actualBlock.append("linha 3\n");
		

		StringBuilder expectedBlock = new StringBuilder();
		
		expectedBlock.append("linha 1\n");
		expectedBlock.append("linha 2\n");
		expectedBlock.append("linha 3\n");
		
		try {
			JUnitUtils.assertBlockEquals("Comparação de blocos de código diferentes", expectedBlock.toString(), actualBlock.toString());
		} catch (ComparisonFailure e) {
			assertEquals("Comparação de blocos de código diferentes", "Comparação de blocos de código diferentes (linha 2) expected:<[linha 2]> but was:<[-- diferente --]>", e.getMessage());
		}
	}
	
	@Test
	public void doAssertBlockDiffSizesTest() {
		StringBuilder actualBlock = new StringBuilder();
		
		actualBlock.append("linha 1\n");
		actualBlock.append("linha 2\n");
		

		StringBuilder expectedBlock = new StringBuilder();
		
		expectedBlock.append("linha 1\n");
		expectedBlock.append("linha 2\n");
		expectedBlock.append("linha 3\n");
		expectedBlock.append("linha 4\n");
		
		try {
			JUnitUtils.assertBlockEquals("Comparação de blocos de código diferentes", expectedBlock.toString(), actualBlock.toString());
		} catch (ComparisonFailure e) {
			assertEquals("Comparação de blocos de código de tamanhos diferentes", "Comparação de blocos de código diferentes (list sizes diff) expected:<linha 1\nlinha 2\n[linha 3\nlinha 4\n]> but was:<linha 1\nlinha 2\n[]>", e.getMessage());
		}
	}
}
