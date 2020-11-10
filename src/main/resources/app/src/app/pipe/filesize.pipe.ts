import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filesize',
})
export class FilesizePipe implements PipeTransform {
  units = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

  transform(bytes: number, precision: number): String {
    if (bytes < 1) {
      return '0 B';
    }

    // validate 'precision'
    if (isNaN(precision)) {
      precision = 1;
    }

    var unitIndex = Math.floor(Math.log(bytes) / Math.log(1000)),
      value = bytes / Math.pow(1000, unitIndex);

    return value.toFixed(precision) + ' ' + this.units[unitIndex];
  }
}
