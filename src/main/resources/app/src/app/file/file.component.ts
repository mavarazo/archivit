import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FileService } from '../service/file.service';
import { File } from '../service/file.model';
import { Observable, of } from 'rxjs';
import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  map,
  tap,
  switchMap,
} from 'rxjs/operators';

import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { Tag } from '../service/tag.model';
import { TagService } from '../service/tag.service';

@Component({
  selector: 'app-file',
  templateUrl: './file.component.html',
  styleUrls: ['./file.component.scss'],
})
export class FileComponent implements OnInit {
  files: File[];
  file: File;

  constructor(
    private route: ActivatedRoute,
    private fileService: FileService,
    private tagService: TagService
  ) {}

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

  @ViewChild('instance', { static: true }) instance: NgbTypeahead;
  model: Tag;
  searching = false;
  searchFailed = false;

  search = (tag$: Observable<String>) =>
    tag$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap((term) =>
        this.tagService.search(term).pipe(
          tap(() => (this.searchFailed = false)),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          })
        )
      ),
      tap(() => (this.searching = false))
    );
}
