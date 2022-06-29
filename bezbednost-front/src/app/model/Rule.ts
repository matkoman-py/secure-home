export interface Rule {
    deviceId: number,
    lowerValue: number,
    upperValue: number,
}

export interface RulePreview {
    id: number,
    device: string,
    lowerValue: number,
    upperValue: number
}