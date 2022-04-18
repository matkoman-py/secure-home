export interface CertificateDTO {
  algorithm: string;
  entryName: string;
  expirationDate: string;
  keySize: number;
  issuerName: string;
  serialNo: number;
  startDate: string;
  subjectName: string;
  version: number;
  format: string;
  valid: boolean;
  reason: string;
}

export interface ExtensionsDTO {
  extendedKeyUsages: string[];
  keyUsages: string[];
  subjectAlternativeNames: string[];
  ca: number;
  aki: string;
  ski: string;
  ekucrit: boolean;
  kucrit: boolean;
  sancrit: boolean,
  bccrit: boolean,
  akicrit: boolean,
  skicrit: boolean
}
