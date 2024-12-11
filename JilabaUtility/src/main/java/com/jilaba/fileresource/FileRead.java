package com.jilaba.fileresource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.CodeSource;

import com.jilaba.common.ReturnStatus;
import com.jilaba.exception.ErrorHandling;
import com.jilaba.exception.JilabaException;
import com.jilaba.security.Validation;
import com.jilaba.sqlaction.ConnectionEnum.ServerType;

public class FileRead {
	private ReturnStatus returnStatus = new ReturnStatus();
	private CodeSource codeSource;
	private String strFilePath = "";
	private String serverFiles = "";
	private String serverFilePath = "/ServerFiles/";

	public ReturnStatus read(Class<?> appClass, JilabaFile jilabaFile, String serverFile) {
		try {

			codeSource = appClass.getProtectionDomain().getCodeSource();
			serverFiles = serverFile;

			if (jilabaFile == JilabaFile.SERVER) {
				returnStatus = serverRead();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			} else if (jilabaFile == JilabaFile.REGISTER) {
				returnStatus = registerRead();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			} else if (jilabaFile == JilabaFile.COMPANY) {
				returnStatus = companyRead();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			} else if (jilabaFile == JilabaFile.HOSERVER) {
				returnStatus = hoServerRead();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			} else {
				throw new JilabaException("Invalid Jilaba File Parameter");
			}
			return new ReturnStatus(true);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	public ReturnStatus read(Class<?> appClass, JilabaFile jilabaFile) {
		try {

			codeSource = appClass.getProtectionDomain().getCodeSource();
			serverFiles = "";

			if (jilabaFile == JilabaFile.SERVER) {
				returnStatus = serverRead();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			} else if (jilabaFile == JilabaFile.REGISTER) {
				returnStatus = registerRead();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			} else if (jilabaFile == JilabaFile.COMPANY) {
				returnStatus = companyRead();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			} else if (jilabaFile == JilabaFile.HOSERVER) {
				returnStatus = hoServerRead();
				if (!returnStatus.isStatus())
					throw new JilabaException(returnStatus.getDescription());
			} else {
				throw new JilabaException("Invalid Jilaba File Parameter");
			}
			return new ReturnStatus(true);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private String getFileName(JilabaFile jilabaFile) throws Exception {
		String fileName = "";
		switch (jilabaFile) {
			case COMPANY:
				fileName = "Company";
				break;
			case REGISTER:
				fileName = "Reg";
				break;
			case SERVER:
				fileName = serverFiles + "Server";
				break;
			case HOSERVER:
				fileName = "HOServer";
				break;
			default:
				break;
		}
		return fileName;
	}

	private String getFileExtension(JilabaFileExtension jilabaFileExtension) throws Exception {
		String extension = "";
		switch (jilabaFileExtension) {
			case MEM:
				extension = ".mem";
				break;
			case COMMON:
				extension = ".Common";
				break;
			case INI:
				extension = ".Ini";
				break;
			default:
				break;
		}
		return extension;
	}

	private void getServerFileName(JilabaFile jilabaFile, File sourceFile) {
		try {
			if (!serverFiles.isEmpty() && jilabaFile.equals(JilabaFile.SERVER))
				strFilePath = sourceFile.getParentFile().getPath() + serverFilePath;
			else
				strFilePath = sourceFile.getParentFile().getPath();

		} catch (Exception e) {
			throw new JilabaException(ErrorHandling.handleError(e));
		}
	}

	private BufferedReader readFileFromDrive(JilabaFile jilabaFile) throws JilabaException {
		try {
			File sourceFile = new File(codeSource.getLocation().toURI().getPath());
			getServerFileName(jilabaFile, sourceFile);

			String fileName = getFileName(jilabaFile);
			String fileNameExtension = fileName + getFileExtension(JilabaFileExtension.COMMON);

			File file = new File(strFilePath + File.separator + fileNameExtension);
			if (!file.exists()) {
				fileNameExtension = fileName + getFileExtension(JilabaFileExtension.INI);
				file = new File(strFilePath + File.separator + fileNameExtension);
				if (!file.exists())
					throw new JilabaException(fileName + " File Not Found..!");
			}

			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			return bufferedReader;
		} catch (Exception e) {
			throw new JilabaException(e);
		}
	}

	private BufferedReader readFileFromDrive(String fileName) throws JilabaException {
		try {
			File sourceFile = new File(codeSource.getLocation().toURI().getPath());
			strFilePath = sourceFile.getParentFile().getPath();

			File file = new File(strFilePath + File.separator + fileName);
			if (!file.exists())
				throw new JilabaException(fileName + " File Not Found..!");

			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			return bufferedReader;
		} catch (Exception e) {
			throw new JilabaException(e);
		}
	}

	private ReturnStatus registerRead() {
		try {
			String readedValue;
			int intLineCount = 0;

			BufferedReader bufferedReader = readFileFromDrive(JilabaFile.REGISTER);

			while ((readedValue = bufferedReader.readLine()) != null) {
				if (intLineCount == 0) {
					for (int i = 0; i < readedValue.length(); i++) {
						Character character = readedValue.charAt(i);
						if (character == 0xFEFF) {
							readedValue = readedValue.replace(readedValue.charAt(i), Character.MIN_VALUE);
							readedValue = readedValue.trim();
						}
					}
					Register.setCompanyId(Validation.decrypt(readedValue));
				} else if (intLineCount == 1) {
					Register.setCompanyName(Validation.decrypt(readedValue));
				} else if (intLineCount == 2) {
					Register.setCompanyShtName(Validation.decrypt(readedValue));
				} else if (intLineCount == 3) {
					Register.setAddress1(Validation.decrypt(readedValue));
				} else if (intLineCount == 4) {
					Register.setAddress2(Validation.decrypt(readedValue));
				} else if (intLineCount == 5) {
					Register.setCityCountry(Validation.decrypt(readedValue));
				} else if (intLineCount == 6) {
					Register.setPinCode(Validation.decrypt(readedValue));
				} else if (intLineCount == 7) {
					Register.setPhone(Validation.decrypt(readedValue));
				} else if (intLineCount == 8) {
					Register.setEmail(Validation.decrypt(readedValue));
				} else if (intLineCount == 9) {
					Register.setCurrencyName(Validation.decrypt(readedValue));
				} else if (intLineCount == 10) {
					Register.setCurrencyCode(Validation.decrypt(readedValue));
				} else if (intLineCount == 11) {
					Register.setDecimalpoint(Validation.decrypt(readedValue));
				} else if (intLineCount == 12) {
					Register.setNoOfUser(Integer.parseInt(Validation.decrypt(readedValue)));
				} else if (intLineCount == 13) {
					Register.setTimeOut(Integer.parseInt(Validation.decrypt(readedValue)));
				} else if (intLineCount == 14) {
					Register.setKeyCode(Validation.decrypt(readedValue));
				} else if (intLineCount == 15) {
					Register.setPackageName(Validation.decrypt(readedValue));
				} else if (intLineCount == 16) {
					Register.setCompanyType(Validation.decrypt(readedValue));
				} else if (intLineCount == 17) {
					Register.setAllModules(Validation.decrypt(readedValue));
				} else if (intLineCount == 18) {
					Register.setNoOfCompany(Integer.parseInt(Validation.decrypt(readedValue)));
				} else if (intLineCount == 19) {
					Register.setCostCentreReq(Validation.decrypt(readedValue));
				} else if (intLineCount == 20) {
					Register.setNoOfCostCentre(Integer.parseInt(Validation.decrypt(readedValue)));
				} else if (intLineCount == 21) {
					Register.setPackageCode(Integer.parseInt(Validation.decrypt(readedValue)));
				} else if (intLineCount == 22) {
					Register.setModules(Validation.decrypt(readedValue));

				}
				intLineCount++;
			}
			bufferedReader.close();

			return new ReturnStatus(true, "");
		} catch (NumberFormatException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (IOException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus serverRead() {
		try {
			String readedValue;
			int intLineCount = 0;

			BufferedReader bufferedReader = readFileFromDrive(JilabaFile.SERVER);

			while ((readedValue = bufferedReader.readLine()) != null) {
				if (intLineCount == 0) {
					for (int i = 0; i < readedValue.length(); i++) {
						Character character = readedValue.charAt(i);
						if (character == 0xFEFF) {
							readedValue = readedValue.replace(readedValue.charAt(i), Character.MIN_VALUE);
							readedValue = readedValue.trim();
						}
					}
					Server.setServerName(readedValue);
				} else if (intLineCount == 1) {
					Server.setPortNo(Long.parseLong(Validation.decrypt(readedValue)));
				} else if (intLineCount == 2) {
					Server.setUserName(Validation.decrypt(readedValue));
				} else if (intLineCount == 3) {
					Server.setPassword(Validation.decrypt(readedValue));
				} else if (intLineCount == 4) {
					Server.setDatabasePath(Validation.decrypt(readedValue));
				} else if (intLineCount == 5) {
					if ("S".equalsIgnoreCase(Validation.decrypt(readedValue))) {
						Server.setServerType(ServerType.MSSQL);
					} else {
						Server.setServerType(ServerType.MYSQL);
					}
				}
				intLineCount++;
			}
			bufferedReader.close();
			return new ReturnStatus(true);
		} catch (NumberFormatException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (IOException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus hoServerRead() {
		try {
			String readedValue;
			int intLineCount = 0;

			BufferedReader bufferedReader = readFileFromDrive(JilabaFile.HOSERVER);

			while ((readedValue = bufferedReader.readLine()) != null) {
				if (intLineCount == 0) {
					for (int i = 0; i < readedValue.length(); i++) {
						Character character = readedValue.charAt(i);
						if (character == 0xFEFF) {
							readedValue = readedValue.replace(readedValue.charAt(i), Character.MIN_VALUE);
							readedValue = readedValue.trim();
						}
					}
					HOServer.setServerName(readedValue);
				} else if (intLineCount == 1) {
					HOServer.setPortNo(Long.parseLong(Validation.decrypt(readedValue)));
				} else if (intLineCount == 2) {
					HOServer.setUserName(Validation.decrypt(readedValue));
				} else if (intLineCount == 3) {
					HOServer.setPassword(Validation.decrypt(readedValue));
				} else if (intLineCount == 4) {
					HOServer.setDatabasePath(Validation.decrypt(readedValue));
				} else if (intLineCount == 5) {
					if ("S".equalsIgnoreCase(Validation.decrypt(readedValue))) {
						HOServer.setServerType(ServerType.MSSQL);
					} else {
						HOServer.setServerType(ServerType.MYSQL);
					}
				}
				intLineCount++;
			}
			bufferedReader.close();
			return new ReturnStatus(true);
		} catch (NumberFormatException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (IOException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}

	private ReturnStatus companyRead() {
		try {
			String readedValue;
			int intLineCount = 0;
			String fileName = getFileName(JilabaFile.COMPANY) + getFileExtension(JilabaFileExtension.MEM);

			BufferedReader bufferedReader = readFileFromDrive(fileName);
			while ((readedValue = bufferedReader.readLine()) != null) {
				readedValue = readedValue.substring(readedValue.indexOf(":") + 1).trim();
				if (intLineCount == 0) {
					Company.setCompanyCode(readedValue);
				} else if (intLineCount == 1) {
					Company.setCompanyName(readedValue);
				} else if (intLineCount == 2) {
					Company.setServerName(readedValue);
				} else if (intLineCount == 3) {
					Company.setPortNo(Long.parseLong(readedValue));
				} else if (intLineCount == 4) {
					Company.setUserName(readedValue);
				} else if (intLineCount == 5) {
					Company.setPassword(Validation.decrypt(readedValue));
				} else if (intLineCount == 6) {
					Company.setDatabasePath(readedValue);
				} else if (intLineCount == 7) {
					if ("S".equalsIgnoreCase(readedValue)) {
						Company.setServerType(ServerType.MSSQL);
					} else if ("M".equalsIgnoreCase(readedValue)) {
						Company.setServerType(ServerType.MYSQL);
					}
				}
				intLineCount++;
			}
			bufferedReader.close();
			return new ReturnStatus(true);
		} catch (NumberFormatException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (IOException e) {
			return new ReturnStatus(false, e.getMessage(), e);
		} catch (Exception e) {
			return new ReturnStatus(false, e.getMessage(), e);
		}
	}
}
