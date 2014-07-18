/**
 * Copyright 2011 Marco Berri - marcoberri@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package it.marcoberri.mbfasturl.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class UAgent {

	// "versionNumber\" : { \"extension\" : \"_13\" , \"groups\" : [ \"1\" , \"6\" , \"0\"]}} , \"producer\" : \"Sun Microsystems, Inc.\" , \"producerUrl\" : \"http://www.sun.com/\" , \"type\" : \"LIBRARY\" , \"typeName\" : \"Library\" , \"url\" : \"http://www.sun.com/java/\" , \"versionNumber\" : { \"extension\" : \"_13\" , \"groups\" : [ \"1\" , \"6\" , \"0\"]}}"
	public static class OS {

		private String family;
		private String familyName;
		private String icon;
		private String name;
		private String producer;
		private String producerUrl;
		private String url;
		private VN versionNumber;

		/**
		 * @return the family
		 */
		public String getFamily() {
			return family;
		}

		/**
		 * @return the familyName
		 */
		public String getFamilyName() {
			return familyName;
		}

		/**
		 * @return the icon
		 */
		public String getIcon() {
			return icon;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the producer
		 */
		public String getProducer() {
			return producer;
		}

		/**
		 * @return the producerUrl
		 */
		public String getProducerUrl() {
			return producerUrl;
		}

		/**
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * @return the versionNumber
		 */
		public VN getVersionNumber() {
			if (versionNumber == null)
				versionNumber = new VN();
			return versionNumber;
		}

		/**
		 * @param family
		 *            the family to set
		 */
		public void setFamily(String family) {
			this.family = family;
		}

		/**
		 * @param familyName
		 *            the familyName to set
		 */
		public void setFamilyName(String familyName) {
			this.familyName = familyName;
		}

		/**
		 * @param icon
		 *            the icon to set
		 */
		public void setIcon(String icon) {
			this.icon = icon;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @param producer
		 *            the producer to set
		 */
		public void setProducer(String producer) {
			this.producer = producer;
		}

		/**
		 * @param producerUrl
		 *            the producerUrl to set
		 */
		public void setProducerUrl(String producerUrl) {
			this.producerUrl = producerUrl;
		}

		/**
		 * @param url
		 *            the url to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}

		/**
		 * @param versionNumber
		 *            the versionNumber to set
		 */
		public void setVersionNumber(VN versionNumber) {
			this.versionNumber = versionNumber;
		}
	}

	public static class VN {

		private String extension;
		private List<String> groups;
		private String minor;
		private String major;

		/**
		 * @return the extension
		 */
		public String getExtension() {
			return extension;
		}

		/**
		 * @return the groups
		 */
		public List<String> getGroups() {
			if (this.groups == null)
				groups = new ArrayList();
			return groups;
		}

		/**
		 * @return the major
		 */
		public String getMajor() {
			return major;
		}

		/**
		 * @return the minor
		 */
		public String getMinor() {
			return minor;
		}

		/**
		 * @param extension
		 *            the extension to set
		 */
		public void setExtension(String extension) {
			this.extension = extension;
		}

		/**
		 * @param groups
		 *            the groups to set
		 */
		public void setGroups(List<String> groups) {
			this.groups = groups;
		}

		/**
		 * @param major
		 *            the major to set
		 */
		public void setMajor(String major) {
			this.major = major;
		}

		/**
		 * @param minor
		 *            the minor to set
		 */
		public void setMinor(String minor) {
			this.minor = minor;
		}

	}

	private String family;
	private String icon;
	private String name;
	private OS operatingSystem = new OS();
	private String producer;
	private String producerUrl;

	private String url;

	private String typeName;

	/**
	 * @return the family
	 */
	public String getFamily() {
		return family;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the operatingSystem
	 */
	public OS getOperatingSystem() {
		if (operatingSystem == null)
			operatingSystem = new OS();
		return operatingSystem;
	}

	/**
	 * @return the producer
	 */
	public String getProducer() {
		return producer;
	}

	/**
	 * @return the producerUrl
	 */
	public String getProducerUrl() {
		return producerUrl;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param family
	 *            the family to set
	 */
	public void setFamily(String family) {
		this.family = family;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param operatingSystem
	 *            the operatingSystem to set
	 */
	public void setOperatingSystem(OS operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	/**
	 * @param producer
	 *            the producer to set
	 */
	public void setProducer(String producer) {
		this.producer = producer;
	}

	/**
	 * @param producerUrl
	 *            the producerUrl to set
	 */
	public void setProducerUrl(String producerUrl) {
		this.producerUrl = producerUrl;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
