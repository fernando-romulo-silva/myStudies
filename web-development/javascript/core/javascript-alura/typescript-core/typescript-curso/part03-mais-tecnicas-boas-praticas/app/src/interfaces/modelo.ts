import { Imprimivel } from "../utils/imprimivel";
import { Comparavel } from "./comparavel";

export interface Modelo<T> extends Comparavel<T>, Imprimivel {}
