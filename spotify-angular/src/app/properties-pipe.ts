import { Pipe, PipeTransform } from '@angular/core';

/**
 * Convert Object to array of keys.
 */
@Pipe({
  name: 'appProperties'
})
export class PropertiesPipe implements PipeTransform {

  transform(value: {}): string[] {

    if (!value) {
      return [];
    }
    let keys = Object.keys(value);
    let ret = [];
    for(let key of keys){
        ret.push({name : key , items: value[key]})
    }

    return ret;
  }
}