import {Pipe} from "@angular/core";
import {PipeTransform} from "@angular/core";

@Pipe({name: 'capitalize'})
export class CapitalizePipe implements PipeTransform {

    transform(value:any) {
        if (value) {
            return value.replace(/\b\w/g, symbol => symbol.toLocaleUpperCase())
        }
        return value;
    }

}