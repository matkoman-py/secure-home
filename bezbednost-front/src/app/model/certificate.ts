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
}