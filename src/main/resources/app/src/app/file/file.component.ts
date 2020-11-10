import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FileService } from '../service/file.service';

@Component({
  selector: 'app-file',
  templateUrl: './file.component.html',
  styleUrls: ['./file.component.scss'],
})
export class FileComponent implements OnInit {

  files: any;
  file: any;

  constructor(private route: ActivatedRoute, private fileService: FileService) {  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      if (params.has('fileId')) {
        this.findById(+params.get('fileId'));
      }
    });
    
    this.route.queryParamMap.subscribe((params) => {
      if (params.keys.length == 0) {
        this.findAllFiles();
      } else if (params.has('filter')) {
        if (params.get('filter') == 'untagged') {
          return this.findAllByTagsIsEmpty();
        }
      }
    });
  }

  findAllFiles() {
    this.fileService.findAll().subscribe((data) => {
      this.files = data;
    });
  }

  findById(id: number) {
    this.fileService.findById(id).subscribe((data) => {
      this.file = data;
    });
  }

  findAllByTagsIsEmpty() {
    this.fileService.findAllByTagsIsEmpty().subscribe((data) => {
      this.files = data;
    });
  }
}
