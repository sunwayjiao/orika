/*
 * Orika - simpler, better and faster Java bean mapping
 * 
 * Copyright (C) 2011 Orika authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ma.glasnost.orika.test.primitives;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.test.MappingUtil;

import org.junit.Assert;
import org.junit.Test;

public class PrimitivesTestCase {

	@Test
	public void testPrimitivesToWrapper() {
		MapperFactory factory = MappingUtil.getMapperFactory();
		MapperFacade mapper = factory.getMapperFacade();

		PrimitiveAttributes source = new PrimitiveAttributes();

		source.setAge(27);
		source.setName("PPPPP");
		source.setSex('H');
		source.setVip(true);

		WrapperAttributes destination = mapper.map(source, WrapperAttributes.class);

		Assert.assertEquals(Integer.valueOf(source.getAge()), destination.getAge());
		Assert.assertEquals(source.getName(), destination.getName());
		Assert.assertEquals(Character.valueOf(source.getSex()), destination.getSex());
		Assert.assertEquals(source.getVip(), destination.getVip());

	}

	@Test
	public void testWrapperToPrimitives() {
		MapperFactory factory = MappingUtil.getMapperFactory();
		MapperFacade mapper = factory.getMapperFacade();

		WrapperAttributes source = new WrapperAttributes();

		source.setAge(27);
		source.setShortAge((short)27);
		source.setName("PPPPP");
		source.setSex('H');
		source.setVip(true);

		PrimitiveAttributes destination = mapper.map(source, PrimitiveAttributes.class);

		Assert.assertEquals(source.getAge(), Integer.valueOf(destination.getAge()));
		Assert.assertEquals(source.getName(), destination.getName());
		Assert.assertEquals(source.getSex(), Character.valueOf(destination.getSex()));
		Assert.assertEquals(source.getVip(), destination.getVip());

	}
	
	@Test
	public void testWrapperToWrapper() {
		MapperFactory factory = MappingUtil.getMapperFactory();
		MapperFacade mapper = factory.getMapperFacade();

		WrapperAttributes source = new WrapperAttributes();

		source.setAge(27);
		source.setShortAge((short)27);
		source.setName("PPPPP");
		source.setSex('H');
		source.setVip(true);

		OtherWrapperAttributes destination = mapper.map(source, OtherWrapperAttributes.class);

		Assert.assertEquals(source.getAge(), Integer.valueOf(destination.getAge()));
		Assert.assertEquals(source.getName(), destination.getName());
		Assert.assertEquals(source.getSex(), Character.valueOf(destination.getSex()));
		Assert.assertEquals(source.getVip(), destination.getVip());

	}

	public static class PrimitiveAttributes {
		private int age;
		private short shortAge;
		private String name;
		private char sex;
		private boolean vip;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public char getSex() {
			return sex;
		}

		public void setSex(char sex) {
			this.sex = sex;
		}

		public boolean getVip() {
			return vip;
		}

		public void setVip(boolean vip) {
			this.vip = vip;
		}

		public short getShortAge() {
			return shortAge;
		}

		public void setShortAge(short shortAge) {
			this.shortAge = shortAge;
		}

	}

	public static class WrapperAttributes {
		private Integer age;
		private Short shortAge;
		private String name;
		private Character sex;
		private Boolean vip;

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String nom) {
			this.name = nom;
		}

		public Character getSex() {
			return sex;
		}

		public void setSex(Character sex) {
			this.sex = sex;
		}

		public Boolean getVip() {
			return vip;
		}

		public void setVip(Boolean vip) {
			this.vip = vip;
		}

		public Short getShortAge() {
			return shortAge;
		}

		public void setShortAge(Short shortAge) {
			this.shortAge = shortAge;
		}

	}
	
	public static class OtherWrapperAttributes {
		private Integer age;
		private Short shortAge;
		private String name;
		private Character sex;
		private Boolean vip;

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String nom) {
			this.name = nom;
		}

		public Character getSex() {
			return sex;
		}

		public void setSex(Character sex) {
			this.sex = sex;
		}

		public Boolean getVip() {
			return vip;
		}

		public void setVip(Boolean vip) {
			this.vip = vip;
		}

		public Short getShortAge() {
			return shortAge;
		}

		public void setShortAge(Short shortAge) {
			this.shortAge = shortAge;
		}

	}
}
