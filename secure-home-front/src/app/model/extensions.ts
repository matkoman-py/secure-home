export interface Extensions {
  extensions: ExtensionsDTO;
}

export interface ExtensionsDTO {
  basicConstraints: BasicConstraints | null;
  extendedKeyUsage: ExtendedKeyUsage | null;
  keyUsage: KeyUsage | null;
  subjectAlternativeName: SubjectAlternativeName | null;
  bccrit: boolean;
  kuecrit: boolean;
  sancrit: boolean;
  ekucrit: boolean;
  skicrit: boolean;
  skiext: boolean;
  akicrit: boolean;
  akiext: boolean;
}

export interface BasicConstraints {
  ca: boolean;
}
export interface ExtendedKeyUsage {
  keyPurposes: Number[];
}
export interface KeyUsage {
  keyUsages: Number[];
}
export interface SubjectAlternativeName {
  names: Map<number, string>;
}
